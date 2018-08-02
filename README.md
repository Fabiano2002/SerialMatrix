# SerialMatrix
Controls a 8x8 RGB Matrix over a Serial datastream. \n
Requires the jserialcomm library to work. \n
Datastream should look something like this: \n
14 | sync matrix to stream \n
255 255 255 255 255 255 255 255 | colorchannel red row 0 \n
255 255 255 255 255 255 255 255 | colorchannel green row 0 \n
255 255 255 255 255 255 255 255 | colorchannel blue row 0 \n
255 255 255 255 255 255 255 255 | colorchannel red row 1...
