android-perst-tutorial
=====================
####1. Tutorial
<http://www.stevenpan.me/android/perst-try.html>

####2. Notes
#####2.1 Basic Operation
<pre>
Bean or Root should extend form Persistent, and a constructor function with no parameter.

//get  instance of the storage
Storage db = StorageFactory.getInstance().createStorage();

//open the database
db.open("test.dbs", pagePoolSize);
//do somthing with database
db.close();

//the storage can have only one root object
Storage.getRoot();
user needs to register their own root object

//get storage root
MyRootClass root = (MyRootClass)db.getRoot();
if(root == null) {
	root = new MyrootClass(db);
	db.setRoot(root);
}
//get an object using a string key
//perst uses index collection as root object, in java ,object have different lifetime
</pre>

#####2.2 Backup db file
<pre>
//open the backup
Storage db = StorageFactory.getInstance().createStorage();
db.open("test.dbs", pagePoolSize);
 try {
	OutputStream out = new FileOutputString("test.bck");
	db.backup(out);
	out.close();
}catch(IOException x) {
	System.err.println("backup failed:"+x);
}
db.close();
</pre>

#####2.3 import/export(XML)
<pre>
//export to XML
Storage db = StorageFactory.getInstance().createStorage();
db.open("test.dbs", pagePoolSize);
try {
	Writer writer = new BufferedWriter(new FileWriter("test.xml"));
	db.exportXML(writer);
	writer.close();
} catch (IoException x) {
	System.err.println("export failed:"+x);
}
db.close();

//import from XML
db.open("test2.dbs", pagePoolSize);
try {
	Reader reader = new BufferedReader(new FileReader("test.xml"));
	db.importXML(reader);
	reader.close();
}catch (IoException x) {
	System.err.println("export failed:"+x);
}	
db.close();
</pre>
