LIB_DIR = ../lib
CFLAGS = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/win32 -I$(JAVA_HOME)/include/linux -I/usr/local/include -I./
LIBS = -ldaqhats
INSTALL_DIR = /usr/lib
TARGET_LIB = libdaqhats-java.so
TARGET_TEST_LIB = daqhats-java.dll

.PHONY: all all-win-debug clean install uninstall

all: clean $(TARGET_LIB)

all-win-debug: clean $(TARGET_TEST_LIB)

%.o: %.cpp 
	g++ -c -fPIC -o $@ $< $(CFLAGS)

# Building Raspberry PI real library
$(TARGET_LIB): com_github_hpopov_daqhats_TCHatId.o com_github_hpopov_daqhats_ThermoCoupleHat.o
	g++ -shared -fPIC -o $@ $^ -lc $(LIBS)

# Building Windows test library
daqhats-java.dll: com_github_hpopov_daqhats_test_JavaServer.o
	g++ -shared -fPIC -o $@ $^ -Wl,--add-stdcall-alias

clean:
	@rm -f *.o

install: $(TARGET_LIB)
	@install -d $(INSTALL_DIR)
	@install $(TARGET_LIB) $(INSTALL_DIR)

uninstall:
	@rm -rf $(INSTALL_DIR)/$(TARGET_LIB)
