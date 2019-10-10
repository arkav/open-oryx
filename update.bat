@echo off
echo Downloading version.txt
curl https://mysterious-inlet-24356.herokuapp.com/version.txt -o src\main\resources\version.txt
echo Downloading packets.xml
curl https://mysterious-inlet-24356.herokuapp.com/packets.xml -o src\main\resources\packets.xml
echo Downloading objects.xml
curl https://mysterious-inlet-24356.herokuapp.com/objects.xml -o src\main\resources\objects.xml
echo Downloading tiles.xml
curl https://mysterious-inlet-24356.herokuapp.com/tiles.xml -o src\main\resources\tiles.xml
echo Done!
