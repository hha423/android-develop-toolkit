1.为工程添加jni文件夹

2.生成JNIHelper头文件:<br/>
cmd进入bin\classes目录下,运行javah com.example.android.jni.JNIHelper
(前提是保证里面有JNIHelper.class文件存在)

3.将bin\classes目录下生成的头文件件考备到jni文件夹下.<br/>
编写.c或.cpp文件,及Makefile文件

4.编译<br/>
安装ndk目录,并导出环境变量(r8以上版本ndk).
回到工种目录下(此处为AndroidJNIProject),执行ndk-build
