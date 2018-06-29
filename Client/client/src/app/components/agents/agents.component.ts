import { Component, OnInit } from '@angular/core';
import { AID } from '../../interfaces/aid';
import { AType } from '../../interfaces/atype';
import { RestService } from '../../services/rest.service';
import * as $ from 'jquery/dist/jquery.min.js';
import { WebsocketService } from '../../services/websocket.service';

@Component({
  selector: 'app-agents',
  templateUrl: './agents.component.html',
  styleUrls: ['./agents.component.css']
})
export class AgentsComponent implements OnInit {

  constructor(private restService: RestService, private ws:WebsocketService) { }

  ngOnInit() {
    this.restService.getAgentTypes().subscribe(res => {
      console.log("tipovi " + res);
      this.ws.agentTypes = res;
    });
    this.restService.getPerformative().subscribe(res => {
      this.ws.performatives = res;
    });
    this.restService.getRunningAgents().subscribe(res => {
      console.log("running agents: " + res);
      this.ws.runningAgents = res;
    });
  }

  startAgent(name, type) {
    let agent = type.module + "$" + type.name + "/" + name;
    this.restService.startAgent(agent).subscribe(res => console.log(res));
  }

  stopAgent(aid) {
    let agent = aid.name + "$" + aid.host.alias + "$" + aid.host.address + "$" + aid.type.name + "$" + aid.type.module;
    console.log(aid);
    this.restService.stopAgent(agent).subscribe(res => console.log(res));
  }

  sendMessage(message) {
    console.log(message);
  }
}
