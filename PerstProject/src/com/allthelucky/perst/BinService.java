package com.allthelucky.perst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.garret.perst.Index;
import org.garret.perst.Key;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;
import org.garret.perst.XMLImportException;


import android.content.Context;
import android.os.Environment;

/**
 * Perst操作服务类
 * 
 * @author panxw
 * 
 */
public class BinService implements Runnable {
    static BinService INSTANCE;
    static final String fileName = "f.dat";
    static final String dbName = "perst.dbs";
    static final String xmlName = "perst.xml";

    final static int mPagePoolSize = 2 * 1024 * 1024;
    final static int mRecords = 1000;
    
    public String dbPath;
    public Storage db;

    /**
     *构造方法 
     * @param context
     */
    public BinService(Context context) {
        String filePath = getFilePath(context);
        this.dbPath = filePath.replaceAll(fileName, dbName); //设置数�?�库文件路径
        this.db = StorageFactory.getInstance().createStorage();
    }

    /**
     * 获得得测试文件f.dat的路径
     * @param ctx
     * @return
     */
    private String getFilePath(Context ctx) {
        try {
            ctx.openFileOutput(fileName, Context.MODE_PRIVATE).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ctx.getFileStreamPath(fileName).getAbsolutePath();
    }

    /**
     * 获得服务类实例
     * @param context
     * @return
     */
    public static BinService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new BinService(context);
        }
        return INSTANCE;
    }

    /**
     * open database
     */
    private void open() {
        System.out.println("open database");
        db.open(dbPath, mPagePoolSize);
    }

    /**
     * close database
     */
    private void close() {
        System.out.println("close database");
        db.close();
    }

    /**
     * get the root of database
     * @return
     */
    private BinRoot getRoot() {
        BinRoot binRoot = (BinRoot) db.getRoot();
        if (binRoot == null) {
            binRoot = new BinRoot(db);
            db.setRoot(binRoot);
        }
        return binRoot;
    }

    /**
     * add one info item
     * @param bin
     */
    private void insert(Bin bin) {
        BinRoot r = getRoot();
        Index<Bin> numberIndex = r.numberIndex;
        Index<Bin> messageIndex = r.messageIndex;
        numberIndex.put(bin.number, bin);
        messageIndex.put(bin.message, bin);
    }
    
    /**
     * remove one info item
     * @param bin
     */
    private void remove(Bin bin) {
        BinRoot r = getRoot();
        Index<Bin> numberIndex = r.numberIndex;
        numberIndex.remove(new Key(bin.number));
        bin.deallocate();
        db.commit();
    }

    /**
     * check if certain item exist in database
     * @param bin
     * @return
     */
    private boolean hasNumber(Bin bin) {
        BinRoot r = getRoot();
        Index<Bin> numberIndex = r.numberIndex;
        Bin b = (Bin) numberIndex.get(new Key(bin.number));
//        System.out.println(b);
        return b != null && (bin.number).equals(b.number);
    }
    
    /**
     * getAll items from database
     * @return
     */
    private List<Bin> getAll() {
        BinRoot r = getRoot();
        Index<Bin> allIndex = r.numberIndex;
        List<Bin> list = new ArrayList<Bin>();
        for(Bin b: allIndex) {
           System.out.println("--"+b);
           list.add(b);
        }
        return list;
    }

    /**
     * import data from XML file to database
     */
    private void read() {
        System.out.println("import database");
        if (!hasSDCard()) {
            System.out.println("no sdcard");
            return;
        }
        try {
            Reader reader = new BufferedReader(new FileReader(getSDFilePath()),
                    1024 * 1024);
            db.importXML(reader);
            reader.close();
        } catch (XMLImportException e) {
            e.printStackTrace();
        } catch (IOException x) {
            System.err.println("import failed:" + x);
        }
    }

    /**
     * export data to XML file
     */
    private void write() {
        System.out.println("export database");
        if (!hasSDCard()) {
            System.out.println("no sdcard");
            return;
        }
        try {
            Writer writer = new BufferedWriter(new FileWriter(getSDFilePath()),
                    8 * 1024);
            db.exportXML(writer);
            writer.close();
        } catch (IOException x) {
            System.err.println("export failed:" + x);
        }
    }

    private String getSDFilePath() {
        return Environment.getExternalStorageDirectory() + "/" + xmlName;
    }

    private boolean hasSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * batch add to database
     */
    private void generate() {
        System.out.println("generate data");
        long start = System.currentTimeMillis();
        int i;

        for (i = 0; i < mRecords; i++) {
            Bin b = new Bin();
            b.number = Integer.toString(i);
            b.message = Integer.toString(i*i);
            insert(b);
        }
        db.commit(); // Commit current transaction
        System.out.println("inserting " + mRecords + " records: "
                + (System.currentTimeMillis() - start) + " milliseconds");
    }

    /**
     * batch query database
     */
    private void search() {
        System.out.println("search data");
        long start = System.currentTimeMillis();
        int i;

        for (i = 0; i < mRecords; i++) {
            Bin b = new Bin();
            b.number = Integer.toString(i);
            hasNumber(b);
        }

        System.out.println("search " + mRecords + " records: "
                + (System.currentTimeMillis() - start) + " milliseconds");
    }
    
    /**
     * batch delete data
     */
    private void removeAll() {
        System.out.println("remove data");
        long start = System.currentTimeMillis();
        int i;

        for (i = 0; i < mRecords; i++) {
            Bin b = new Bin();
            b.number = Integer.toString(i);
            remove(b);
        }

        System.out.println("remove " + mRecords + " records: "
                + (System.currentTimeMillis() - start) + " milliseconds");
    }
    
    /**
     * 测试
     */
    public void runTest() {
        new Thread(this).start();
    }
    
    /**
     * 测试调用。。。
     */
    @Override
    public void run() {
        open();
        generate();
        getAll();
        write();
        close();

        open();
        read();
        removeAll();
        search();
        getAll();
        close();
    }

}
