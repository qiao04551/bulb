/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.maxzuo.socket.server;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

/***
 * The task handles incoming command request in HTTP protocol.
 *
 * @author youji.zj
 * @author Eric Zhao
 */
public class HttpEventTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HttpEventTask.class);

    private final Socket socket;

    private boolean writtenHead = false;

    public HttpEventTask(Socket socket) {
        this.socket = socket;
    }

    public void close() throws Exception {
        socket.close();
    }

    @Override
    public void run() {
        if (socket == null) {
            return;
        }
        BufferedReader in = null;
        PrintWriter printWriter = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
            OutputStream outputStream = socket.getOutputStream();

            printWriter = new PrintWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));

            String line = in.readLine();
            logger.info("[SimpleHttpCommandCenter] socket income: " + line + "," + socket.getInetAddress());

            if (line.length() > 4 && StringUtils.equalsIgnoreCase("POST", line.substring(0, 4))) {
                int contentLength = -1;
                int bg = -1;
                char[] chars = new char[1024];
                boolean headerEnd = false;
                while (true) {
                    if (!headerEnd) {
                        line = in.readLine();
                        System.out.println(line);
                        // 读取header
                        if (StringUtils.isNotBlank(line)) {
                            int idx2 = line.indexOf(":");
                            String key = line.substring(0, idx2).trim();
                            String value = line.substring(idx2 + 1).trim();
                            if ("Content-Length".equalsIgnoreCase(key)) {
                                contentLength = Integer.parseInt(value);
                                bg = contentLength;
                            }
                        } else {
                            // 请求空行
                            if (contentLength < 1) {
                                break;
                            }
                            headerEnd = true;
                        }
                    } else {
                        // 读body（不能使用readLine() 会导致阻塞）
                        int count = in.read(chars);
                        System.out.println(new String(chars, 0, count));
                        if (bg - count == 0) {
                            break;
                        }
                    }
                }
            }

            writeOkStatusLine(printWriter);
            printWriter.flush();
        } catch (Throwable e) {
            logger.warn("error", e);
        } finally {
            closeResource(in);
            closeResource(printWriter);
            closeResource(socket);
        }
    }

    private void closeResource(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                logger.warn("Close resource failed", e);
            }
        }
    }

    /**
     * Write `400 Bad Request` HTTP response status line and message body, then flush.
     */
    private void badRequest(/*@NonNull*/ final PrintWriter out, String message) {
        out.print("HTTP/1.1 400 Bad Request\r\n"
                + "Connection: close\r\n\r\n");
        out.print(message);
        out.flush();
        writtenHead = true;
    }

    /**
     * Write `500 Internal Server Error` HTTP response status line and message body, then flush.
     */
    private void internalError(final PrintWriter out, String message) {
        out.print("HTTP/1.1 500 Internal Server Error\r\n"
                + "Connection: close\r\n\r\n");
        out.print(message);
        out.flush();
        writtenHead = true;
    }

    /**
     * Write `200 OK` HTTP response status line and flush.
     */
    private void writeOkStatusLine(final PrintWriter out) {
        out.print("HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/plain\r\n"
                + "Content-Length: 21\r\n"
                + "Connection: close\r\n\r\n"
                + "this is response body");
        out.flush();
        writtenHead = true;
    }
}
