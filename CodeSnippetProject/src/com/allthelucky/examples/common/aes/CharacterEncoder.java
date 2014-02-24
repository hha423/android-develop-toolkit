package com.allthelucky.examples.common.aes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;

public abstract class CharacterEncoder {
	/** Stream that understands "printing" */
	protected PrintStream pStream;

	/** Return the number of bytes per atom of encoding */
	abstract protected int bytesPerAtom();

	/** Return the number of bytes that can be encoded per line */
	abstract protected int bytesPerLine();

	/**
	 * 88 * Encode the prefix for the entire buffer. By default is simply 89 * opens the PrintStream for use by the other functions. 90
	 */
	protected void encodeBufferPrefix(OutputStream aStream) throws IOException {
		pStream = new PrintStream(aStream);
	}

	/**
	 * 96 * Encode the suffix for the entire buffer. 97
	 */
	protected void encodeBufferSuffix(OutputStream aStream) throws IOException {
	}

	/**
	 * 102 * Encode the prefix that starts every output line. 103
	 */
	protected void encodeLinePrefix(OutputStream aStream, int aLength) throws IOException {
	}

	/**
	 * 109 * Encode the suffix that ends every output line. By default 110 * this method just prints a <newline> into the output stream. 111
	 */
	protected void encodeLineSuffix(OutputStream aStream) throws IOException {
		pStream.println();
	}

	/** Encode one "atom" of information into characters. */
	abstract protected void encodeAtom(OutputStream aStream, byte someBytes[], int anOffset, int aLength) throws IOException;

	/**
	 * 121 * This method works around the bizarre semantics of BufferedInputStream's 122 * read method. 123
	 */
	protected int readFully(InputStream in, byte buffer[]) throws java.io.IOException {
		for (int i = 0; i < buffer.length; i++) {
			int q = in.read();
			if (q == -1)
				return i;
			buffer[i] = (byte) q;
		}
		return buffer.length;
	}

	/**
	 * 136 * Encode bytes from the input stream, and write them as text characters 137 * to the output stream. This method will run until it exhausts the 138 * input stream, but does not print the line suffix for a final 139 * line that is shorter than bytesPerLine(). 140
	 */
	public void encode(InputStream inStream, OutputStream outStream) throws IOException {
		int j;
		int numBytes;
		byte tmpbuffer[] = new byte[bytesPerLine()];

		encodeBufferPrefix(outStream);

		while (true) {
			numBytes = readFully(inStream, tmpbuffer);
			if (numBytes == 0) {
				break;
			}
			encodeLinePrefix(outStream, numBytes);
			for (j = 0; j < numBytes; j += bytesPerAtom()) {

				if ((j + bytesPerAtom()) <= numBytes) {
					encodeAtom(outStream, tmpbuffer, j, bytesPerAtom());
				} else {
					encodeAtom(outStream, tmpbuffer, j, (numBytes) - j);
				}
			}
			if (numBytes < bytesPerLine()) {
				break;
			} else {
				encodeLineSuffix(outStream);
			}
		}
		encodeBufferSuffix(outStream);
	}

	/**
	 * 173 * Encode the buffer in <i>aBuffer</i> and write the encoded 174 * result to the OutputStream <i>aStream</i>. 175
	 */
	public void encode(byte aBuffer[], OutputStream aStream) throws IOException {
		ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
		encode(inStream, aStream);
	}

	/**
	 * 183 * A 'streamless' version of encode that simply takes a buffer of 184 * bytes and returns a string containing the encoded buffer. 185
	 */
	public String encode(byte aBuffer[]) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
		String retVal = null;
		try {
			encode(inStream, outStream);
			// explicit ascii->unicode conversion
			retVal = outStream.toString("8859_1");
		} catch (Exception IOException) {
			// This should never happen.
			throw new Error("CharacterEncoder.encode internal error");
		}
		return (retVal);
	}

	/**
	 * 202 * Return a byte array from the remaining bytes in this ByteBuffer. 203 *
	 * <P>
	 * 204 * The ByteBuffer's position will be advanced to ByteBuffer's limit. 205 *
	 * <P>
	 * 206 * To avoid an extra copy, the implementation will attempt to return the 207 * byte array backing the ByteBuffer. If this is not possible, a 208 * new byte array will be created. 209
	 */
	private byte[] getBytes(ByteBuffer bb) {
		/*
		 * This should never return a BufferOverflowException, as we're 213 * careful to allocate just the right amount. 214
		 */
		byte[] buf = null;

		/*
		 * 218 * If it has a usable backing byte buffer, use it. Use only 219 * if the array exactly represents the current ByteBuffer. 220
		 */
		if (bb.hasArray()) {
			byte[] tmp = bb.array();
			if ((tmp.length == bb.capacity()) && (tmp.length == bb.remaining())) {
				buf = tmp;
				bb.position(bb.limit());
			}
		}

		if (buf == null) {
			/*
			 * 232 * This class doesn't have a concept of encode(buf, len, off), 233 * so if we have a partial buffer, we must reallocate 234 * space. 235
			 */
			buf = new byte[bb.remaining()];

			/*
			 * 239 * position() automatically updated 240
			 */
			bb.get(buf);
		}

		return buf;
	}

	/**
	 * 248 * Encode the <i>aBuffer</i> ByteBuffer and write the encoded 249 * result to the OutputStream <i>aStream</i>. 250 *
	 * <P>
	 * 251 * The ByteBuffer's position will be advanced to ByteBuffer's limit. 252
	 */
	public void encode(ByteBuffer aBuffer, OutputStream aStream) throws IOException {
		byte[] buf = getBytes(aBuffer);
		encode(buf, aStream);
	}

	/**
	 * 260 * A 'streamless' version of encode that simply takes a ByteBuffer 261 * and returns a string containing the encoded buffer. 262 *
	 * <P>
	 * 263 * The ByteBuffer's position will be advanced to ByteBuffer's limit. 264
	 */
	public String encode(ByteBuffer aBuffer) {
		byte[] buf = getBytes(aBuffer);
		return encode(buf);
	}

	/**
	 * 271 * Encode bytes from the input stream, and write them as text characters 272 * to the output stream. This method will run until it exhausts the 273 * input stream. It differs from encode in that it will add the 274 * line at the end of a final line that is shorter than bytesPerLine(). 275
	 */
	public void encodeBuffer(InputStream inStream, OutputStream outStream) throws IOException {
		int j;
		int numBytes;
		byte tmpbuffer[] = new byte[bytesPerLine()];

		encodeBufferPrefix(outStream);

		while (true) {
			numBytes = readFully(inStream, tmpbuffer);
			if (numBytes == 0) {
				break;
			}
			encodeLinePrefix(outStream, numBytes);
			for (j = 0; j < numBytes; j += bytesPerAtom()) {
				if ((j + bytesPerAtom()) <= numBytes) {
					encodeAtom(outStream, tmpbuffer, j, bytesPerAtom());
				} else {
					encodeAtom(outStream, tmpbuffer, j, (numBytes) - j);
				}
			}
			encodeLineSuffix(outStream);
			if (numBytes < bytesPerLine()) {
				break;
			}
		}
		encodeBufferSuffix(outStream);
	}

	/**
	 * 306 * Encode the buffer in <i>aBuffer</i> and write the encoded 307 * result to the OutputStream <i>aStream</i>. 308
	 */
	public void encodeBuffer(byte aBuffer[], OutputStream aStream) throws IOException {
		ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
		encodeBuffer(inStream, aStream);
	}

	/**
	 * 316 * A 'streamless' version of encode that simply takes a buffer of 317 * bytes and returns a string containing the encoded buffer. 318
	 */
	public String encodeBuffer(byte aBuffer[]) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
		try {
			encodeBuffer(inStream, outStream);
		} catch (Exception IOException) {
			// This should never happen.
			throw new Error("CharacterEncoder.encodeBuffer internal error");
		}
		return (outStream.toString());
	}

	/**
	 * 332 * Encode the <i>aBuffer</i> ByteBuffer and write the encoded 333 * result to the OutputStream <i>aStream</i>. 334 *
	 * <P>
	 * 335 * The ByteBuffer's position will be advanced to ByteBuffer's limit. 336
	 */
	public void encodeBuffer(ByteBuffer aBuffer, OutputStream aStream) throws IOException {
		byte[] buf = getBytes(aBuffer);
		encodeBuffer(buf, aStream);
	}

	/**
	 * 344 * A 'streamless' version of encode that simply takes a ByteBuffer 345 * and returns a string containing the encoded buffer. 346 *
	 * <P>
	 * 347 * The ByteBuffer's position will be advanced to ByteBuffer's limit. 348
	 */
	public String encodeBuffer(ByteBuffer aBuffer) {
		byte[] buf = getBytes(aBuffer);
		return encodeBuffer(buf);
	}

}