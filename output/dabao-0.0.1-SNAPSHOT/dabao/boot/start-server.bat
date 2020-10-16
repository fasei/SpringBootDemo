@echo off
REM 声明采用UTF-8编码
chcp 65001
::先暂停已经运行的，在重新开启
taskkill -f -t -im 66666.exe

start "lock-server" "C:\Program Files\Java\jre1.8.0_144\bin\66666.exe" -Dfile.encoding=utf-8 -jar "demo-0.0.1-SNAPSHOT.jar"
echo "已经开始运行，请查看"
pause