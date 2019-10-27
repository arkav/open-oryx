@echo off
echo Downloading version.txt
curl https://mysterious-inlet-24356.herokuapp.com/txt/version -o build\version.txt
echo Downloading packets.xml
curl https://mysterious-inlet-24356.herokuapp.com/xml/packets -o build\packets.xml
echo Downloading objects.xml
curl https://static.drips.pw/rotmg/production/current/xmlc/Objects.xml -o build\objects.xml
echo Downloading tiles.xml
curl https://static.drips.pw/rotmg/production/current/xmlc/GroundTypes.xml -o build\tiles.xml
echo Done!
