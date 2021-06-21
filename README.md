# RewindReplaySpigot

Retroactive replays for Spigot.

Operates on the idea of there being two "reels", an events reel and a snapshot reel.

Every second, a snapshot of the world is taken, including all entities and chunks.

Supported events are recorded to the event reel, organized by tick.

Creating a replay is dead simple, just take the closest snapshot, then add all the events after that. Refrence implementation is availble at my other repo, (snapshot-replayer)[https://github.com/EggAllocationService/snapshot-replayer]
