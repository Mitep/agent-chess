import { Injectable } from '@angular/core';
const url = 'ws://localhost:8080/AgentWAR/websocket';

@Injectable()
export class WebsocketService {

  ws: WebSocket;

  constructor() { }

  public connect(): void {
    this.ws = new WebSocket(url);
    this.ws.onmessage = function (message) {

    };
  }
}
