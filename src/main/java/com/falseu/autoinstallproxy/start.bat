@echo off

echo 处理中...
set script_dir=%~dp0

java -cp "%script_dir%hutool-all-5.8.28.jar;%script_dir%;." com.falseu.autoinstallproxy.AutoInstallProxy %1
echo 没错误就表示完成,按任意键退出
pause>nul
