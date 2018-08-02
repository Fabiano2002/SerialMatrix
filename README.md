# SerialMatrix
Controls a 8x8 RGB Matrix over a Serial datastream.
Requires the jserialcomm library to work.
Datastream should look something like this:
14 | sync matrix to stream
255 255 255 255 255 255 255 255 | colorchannel red row 0
255 255 255 255 255 255 255 255 | colorchannel green row 0
255 255 255 255 255 255 255 255 | colorchannel blue row 0
255 255 255 255 255 255 255 255 | colorchannel red row 1...
