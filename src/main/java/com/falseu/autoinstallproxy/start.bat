@echo off

echo ������...
set script_dir=%~dp0

java -cp "%script_dir%hutool-all-5.8.28.jar;%script_dir%;." com.falseu.autoinstallproxy.AutoInstallProxy %1
del "%script_dir%..\v2ray-proxy-install.zip"
rd /s /q "%script_dir%..\v2ray-proxy-install"
echo û����ͱ�ʾ���,��������˳�
pause>nul
