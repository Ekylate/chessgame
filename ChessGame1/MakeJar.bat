cd class
del ChessPlay.jar
jar cvmf ../META-INF/MANIFEST.MF ChessPlay.jar *.class ../images/*
echo "La console va se refermer des que vous appuierez sur une nouvelle touche"
pause