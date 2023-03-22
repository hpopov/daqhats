LIB_DIR = ../lib
CFLAGS = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/win32 -I$(JAVA_HOME)/include/linux -I/usr/local/include -I./
LIBS = -ldaqhats

.PHONY: all all-win-debug clean

all: clean libdaqhats-java.so

all-win-debug: clean daqhats-java.dll

%.o: %.cpp 
	g++ -c -fPIC -o $@ $< $(CFLAGS)

# Building Raspberry PI real library
libdaqhats-java.so: com_github_hpopov_daqhats_TCHatId.o com_github_hpopov_daqhats_ThermoCoupleHat.o
	g++ -shared -fPIC -o $@ $^ -lc $(LIBS)

# Building Windows test library
daqhats-java.dll: com_github_hpopov_daqhats_test_JavaServer.o
	g++ -shared -fPIC -o $@ $^ -Wl,--add-stdcall-alias

clean:
	@rm -f *.o
