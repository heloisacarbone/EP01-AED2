dicionario.o: dicionario.h dicionario.c
	gcc -g -c dicionario.c

grafosmapa.o: grafosmapa.h grafosmapa.c
	gcc -g -c grafosmapa.c

dijkstramapa.o: grafosmapa.h dijkstramapa.c
	gcc -g -c dijkstramapa.c

gps.exe: dijkstramapa.c
	gcc -g -o gps.exe dijkstramapa.o grafosmapa.o
	
clean:
	rm -f *.o gps.exe
