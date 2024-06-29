@echo off
REM 声明采用UTF-8编码
chcp 65001

echo 处理中...

set current_dir=%CD%
cd src\main\java\com\falseu\autoinstallproxy
javac.exe -cp "hutool-all-5.8.28.jar;." -encoding UTF-8 -d . AutoInstallProxy.java
java -cp "hutool-all-5.8.28.jar;." com.falseu.autoinstallproxy.AutoInstallProxy release %current_dir%
echo 按任意键退出
pause>nul