@echo off

echo ������...
set script_dir=%~dp0

java -cp "%script_dir%hutool-all-5.8.28.jar;%script_dir%;." com.falseu.autoinstallproxy.AutoInstallProxy
echo û����ͱ�ʾ���,��������˳�
pause>nul
