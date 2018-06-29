import { Injectable } from '@angular/core';
import { AID } from '../interfaces/aid';
import { AType } from '../interfaces/atype';
const url = 'ws://localhost:8080/AgentWAR/websocket';

@Injectable()
export class WebsocketService {

  ws: WebSocket;
  agentTypes: AType[] = [];
  performatives: string[] = [];
  public runningAgents: AID[] = [];

  constructor() { }

  public connect(): void {
    this.ws = new WebSocket(url);
    var w = this;

    this.ws.onmessage = function (message) {
      console.log("Stigla poruka na websocket:  " + message.data);
      var json = JSON.parse(message.data);
      var type = json.type;
      var data = json.data;
      console.log("type: " + type);
      if (type == "start_agent") {
        let aType = { name: data[0].type.name, module: data[0].type.module };
        let aHost = { alias: data[0].host.alias, address: data[0].host.address };
        let aid = { name: data[0].name, host: aHost, type: aType };
        w.runningAgents.push(aid);
      } else if (type == "stop_agent") {

      }
    };
  }
}
