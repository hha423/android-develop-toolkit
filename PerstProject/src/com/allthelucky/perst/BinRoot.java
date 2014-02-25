package com.allthelucky.perst;

import org.garret.perst.Index;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;

/**
 * 索引类
 */
public class BinRoot extends Persistent {
    public   Index<Bin> numberIndex;
    public Index<Bin> messageIndex;

    public BinRoot() {

    }

    public BinRoot(Storage db) {
        numberIndex = db.<Bin> createIndex(String.class, true);
        messageIndex = db.<Bin> createIndex(String.class, false);
    }
}