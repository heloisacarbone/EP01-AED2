grafosmapa.o: grafosmapa.h grafosmapa.c
	gcc -g -c grafosmapa.c
	
indheap.o: indheap.h indheap.c
	gcc -g -c indheap.c

dijkstramapa.o: grafosmapa.h dijkstramapa.c
	gcc -g -c dijkstramapa.c

gps.exe: dijkstramapa.c
	gcc -g -o gps.exe dijkstramapa.o grafosmapa.o
	
clean:
	rm -f *.o gps.exe
