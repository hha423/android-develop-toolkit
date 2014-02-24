android-perst-project
=====================

//继承自Persistent类的信息类或Root类都必需有无参构造函数.否则会运行出错

for()完之后再提交事务,否则很慢commit

//get  instance of the storage<br/>
Storage db = StorageFactory.getInstance().createStorage();<br/>

//open the database<br/>
db.open("test.dbs", pagePoolSize);<br/>
//do somthing with database<br/>
db.close();<br/>

//the storage can have only one root object<br/>
Storage.getRoot();<br/>
user needs to register their own root object<br/>

//get storage root<br/>
MyRootClass root = (MyRootClass)db.getRoot();<br/>
if(root == null) {<br/>
	root = new MyrootClass(db);<br/>
	db.setRoot(root);<br/>
}<br/>

//get an object using a string key<br/>
//perst uses index collection as root object<br/>
//iin java ,object have different lifetime<br/>

//open the backup<br/>
Storage db = StorageFactory.getInstance().createStorage();<br/>
db.open("test.dbs", pagePoolSize);<br/>
 try {<br/>
	OutputStream out = new FileOutputString("test.bck");<br/>
	db.backup(out);<br/>
	out.close();<br/>
}catch(IOException x) {<br/>
	System.err.println("backup failed:"+x);<br/>
}<br/>
db.close();<br/>


//import/output XML<br/>
Storage db = StorageFactory.getInstance().createStorage();<br/>
db.open("test.dbs", pagePoolSize);<br/>
try {<br/>
	Writer writer = new BufferedWriter(new FileWriter("test.xml"));<br/>
	db.exportXML(writer);<br/>
	writer.close();<br/>
} catch (IoException x) {<br/>
	System.err.println("export failed:"+x);<br/>
}<br/>
db.close();<br/>

db.open("test2.dbs", pagePoolSize);<br/>

try {<br/>
	Reader reader = new BufferedReader(new FileReader("test.xml"));<br/>
	db.importXML(reader);<br/>
	reader.close();<br/>
}catch (IoException x) {<br/>
	System.err.println("export failed:"+x);<br/>
}	<br/>
db.close();<br/>
