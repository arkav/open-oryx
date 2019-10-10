# Usage

Open Oryx is a networking library for RotMG out of the box, that includes alot of out of the box functionality that can be found in the `impl` package.

 - A bare bones `Client`
 - A more robust `CombatClient`
 - A MITM network proxy `Proxy`

## Basic usage

A client can be connected to the game quite simply using `Client`.
```
// Required setup functions that must be called before using open oryx
PacketMapper.mapIds();
// Optional setup functions
ServerMapper.mapServers(false);
GameInfoMapper.mapObjects();
GameInfoMapper.mapGroundTypes();
// Create our client
Client client = new Client(
    new Account("guid", "paswerd132")
);
client.connect(
    ServerMapper.get("AsiaSouthEast"),
    new GameState("X31.7.0")
);
```
An example proxy implementation
```
try {
    ServerSocket listener = new ServerSocket(2050);
    Logger.log("Open Oryx", "Proxy started on port 2050");
    ExecutorService pool = Executors.newFixedThreadPool(50);
    while (true) {
        pool.execute(new Proxy(listener.accept(), ServerMapper.get("USSouthWest")));
    }
} catch (Exception e) {
        e.printStackTrace();
}
```

## Extending impl classes

Classes in `impl` are built to be extended upon
```
class MyClient extends Client {
    public MyClient(Account acc) {
        super(acc);
        this.ls.hook(PacketType.MAPINFO, p -> {
            System.out.println("Hello " + this.mapInfo.getName());
        });
    }
}
```
