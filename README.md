# SerialMatrix
Controls a 8x8 RGB Matrix over a Serial datastream. <br />
Requires the jserialcomm library to work. <br />
Datastream should look something like this: <br />
14 | sync matrix to stream <br />
255 255 255 255 255 255 255 255 | colorchannel red row 0 <br />
255 255 255 255 255 255 255 255 | colorchannel green row 0 <br />
255 255 255 255 255 255 255 255 | colorchannel blue row 0 <br />
255 255 255 255 255 255 255 255 | colorchannel red row 1... <br />
