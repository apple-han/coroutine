const net = require('net');

const Server = net.createServer(), clientList = [];

Server.on("connection",function(client){
    client.name = client.remoteAddress + ":" + client.remotePort;
    client.write("Accepting incoming from "+client.name+" \n");
    clientList.push(client);

    client.on("data",function(data){
        broadcast(data,client);
    });

    client.on("end",function(){
        clientList.splice(clientList.indexOf(client),1);
    });

    client.on("error",function(e){
        console.log(e)
    });
});
Server.listen(2678,"0.0.0.0")

function broadcast(message,client){
    const cleanup = [];
    for(let i=0; i<clientList.length; i++){
        if(client == clientList[i]){
            if(clientList[i].writable){
                clientList[i].write(client.name = "hello "+message);
            }else{
                cleanup.push[clientList[i]];
                clientList[i].destory();
            }
        }
    }
}
