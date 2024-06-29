@echo off
REM 声明采用UTF-8编码
chcp 65001

echo 处理中...
java -cp "hutool-all-5.8.28.jar;." com.falseu.autoinstallproxy.AutoInstallProxy
echo 没错误就表示完成,按任意键退出
pause>nul
