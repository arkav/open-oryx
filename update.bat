@echo off
echo Downloading version.txt
curl https://mysterious-inlet-24356.herokuapp.com/txt/version -o src\main\resources\version.txt
echo Downloading packets.xml
curl https://mysterious-inlet-24356.herokuapp.com/xml/packets -o src\main\resources\packets.xml
echo Downloading objects.xml
curl https://static.drips.pw/rotmg/production/current/xmlc/Objects.xml -o src\main\resources\objects.xml
echo Downloading tiles.xml
curl https://static.drips.pw/rotmg/production/current/xmlc/GroundTypes.xml -o src\main\resources\tiles.xml
echo Done!
