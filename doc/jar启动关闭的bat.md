# jar便捷启动方式

1. 找到本地java的路径，找到 **C:\Program Files\Java\jre1.8.0_144\bin** 目录，将目录下的javaw.exe 复制一份修改为新的名称如 **66666.exe**

2. 编写启动服务的bat文件

 ```

 @echo off

start "lock-server" "C:\Program Files\Java\jre1.8.0_144\bin\66666.exe" -Dfile.encoding=utf-8 -jar "C:\Users\JzJ\IdeaProjects\demo\output\dabao-0.0.1-SNAPSHOT\dabao\boot\demo-0.0.1-SNAPSHOT.jar"

pause
 
 ```

3. 编写停止服务的bat文件


```
@echo off

taskkill -f -t -im 66666.exe

pause

```