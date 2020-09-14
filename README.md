# Mortar

#### 介绍
今年年初启动的项目中计划使用`JetPack`的`Navigation`框架，于是如何在Fragment之间传递数据就成为了一个新的问题。  
因为`Fragment`中并没有像`Activity`中`setResult`和`onActivityResult`这样的API，而且`Navigation`对此也是毫无支持。  
虽然在最新的`Fragment-1.3.0-alpha08`中有新加入的API，但是你看到`alpha`几个字符后，还是算了吧。  
于是自己琢磨出了一套方法，相比官方推荐的一系列方法中，这套方法拥有以下优势：
* 使用类型签名的方式进行匹配，不需要传递String类型的Key
* 直接传递任意类型的对象，不需要实现`Serializable`或`Parcelable`接口
* 无需声明接口或ViewModel、LiveData等

#### 安装教程

1.在你的`android`工程的根目录下的`build.gradle`文件中适当的位置添加以下代码：
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2.在你的模块下的`build.gradle`文件中的适当位置添加以下代码：
```
	dependencies {
	        implementation 'com.gitee.numeron:mortar:0.1.0'
	}
```

#### 初始化

1.在启用`Navigation`的`Activity`中的`onCreate`方法中、`setContentView`之前，调用以下方法：
```
mortarEnabled()
```

#### 使用方法
1.在要接收数据的`AFragment`中使用`onResult`方法：
```
onResult<List<User>> { resultCode: Int, list: List<User>? ->
    if(resultCode == RESULT_OK) {
    	if(!list.isNullOrEmpty()) {
            //TODO()
        }
    } else {
    	//TODO()
    }
}
```
2.在要传递数据的`BFragment`中使用`setResult`方法：
```
val userList: List<User> = ...
setResult(RESULT_OK, userList)
```
3.当从`BFragment`返回到`AFragment`时，之前通过`onResult`方法设置的`Lambda`回调就会运行。
* 注：`onResult`和`setResult`传递的参数的类型必需一致，MutableList和List是不一样的，当其中一个为MutableList时，应该将类型声明为具体的List类型。