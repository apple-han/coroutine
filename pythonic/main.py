from socket import *
import asyncio


async def server(address):
    sock = socket(AF_INET, SOCK_STREAM)
    sock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    sock.bind(address)
    sock.listen(1)
    sock.setblocking(False)
    while True:
        client, addr = await loop.sock_accept(sock)
        print("connect from ", addr)
        loop.create_task(handler(client))


async def handler(client):
    with client:
        while True:
            try:
                data = await loop.sock_recv(client, 1024)
                if not data:
                    break
                await loop.sock_sendall(client, str.encode("Hello: ") + data)
            except Exception as e:
                client.close()
                print("connection closed")
                break

loop = asyncio.get_event_loop()
loop.create_task(server(('0.0.0.0', 8080)))
loop.run_forever()

