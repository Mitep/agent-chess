import { Component, OnInit } from '@angular/core';
import { Figure } from '../../interfaces/figure';
import { RestService } from '../../services/rest.service';
import { WebsocketService } from '../../services/websocket.service';

@Component({
  selector: 'app-chess',
  templateUrl: './chess.component.html',
  styleUrls: ['./chess.component.css']
})
export class ChessComponent implements OnInit {

  private pfigures: Figure[] = [];
  private cfigures: Figure[] = [];

  private fields: String[] = [];

  private clickable: number[] = [];

  clicked1: number = -1;
  clicked2: number = -1;

   senderType = "{\"name\":\"ChessPlayerAgent\",\"module\":\"agents.chess\"}";
   senderHost = "{\"alias\":\"master\",\"address\":\"localhost:8080\"}";
   sender = "{\"name\":\"playerChess\","
      + " \"host\":" + this.senderHost + ","
      + " \"type\":" + this.senderType + "}";

    receiverType = "{\"name\":\"ChessMasterAgent\",\"module\":\"agents.chess\"}";
    receiverHost = "{\"alias\":\"master\",\"address\":\"localhost:8080\"}";
    receiver = "{\"name\":\"masterChess\","
      + " \"host\":" + this.receiverHost + ","
      + " \"type\":" + this.receiverType + "}";

  constructor(private service: RestService, private ws: WebsocketService) {
    for (var i = 0; i < 64; i++) {
      this.fields[i] = "../../assets/empty.png";
    }
  }

  ngOnInit() {
  }

  startGame() {
    for (var i = 0; i < 64; i++) {
      this.fields[i] = "../../assets/empty.png";
    }

    this.cfigures = [];
    this.pfigures = [];
    this.clickable = [];
    this.clicked1 = -1;
    this.clicked2 = -1;

    let master = "agents.chess" + "$" + "ChessMasterAgent" + "/" + "masterChess";
    this.service.startAgent(master).subscribe(res => console.log(res));

    let player = "agents.chess" + "$" + "ChessPlayerAgent" + "/" + "playerChess";
    this.service.startAgent(player).subscribe(res => console.log(res));


    

    let aclMsg = "{\"performative\":\"request\","
      + " \"sender\":" + this.sender + ","
      + " \"receivers\":[" + this.receiver + "],"
      + " \"replyTo\":" + null + ","
      + " \"content\":\" \","
      + " \"language\":\" \","
      + " \"encoding\":\" \","
      + " \"ontology\":\" \","
      + " \"protocol\":\" \","
      + " \"conversationId\":\" \","
      + " \"replyWith\":\" \","
      + " \"inReplyTo\":\" \","
      + " \"replyBy\":\" \"}";

    console.log(aclMsg);
    this.service.sendACLMessage(aclMsg).subscribe(res => console.log(res));

    let figure0 = { id: "rook_black0", image: "../../assets/rook_black.png", position: 0 };
    this.fields[0] = figure0.image;
    this.cfigures.push(figure0);

    let figure1 = { id: "knight_black0", image: "../../assets/knight_black.png", position: 1 };
    this.fields[1] = figure1.image;
    this.cfigures.push(figure1);

    let figure2 = { id: "bishop_black0", image: "../../assets/bishop_black.png", position: 2 };
    this.fields[2] = figure2.image;
    this.cfigures.push(figure2);

    let figure3 = { id: "queen_black0", image: "../../assets/queen_black.png", position: 3 };
    this.fields[3] = figure3.image;
    this.cfigures.push(figure3);

    let figure4 = { id: "king_black0", image: "../../assets/king_black.png", position: 4 };
    this.fields[4] = figure4.image;
    this.cfigures.push(figure4);

    let figure5 = { id: "bishop_black1", image: "../../assets/bishop_black.png", position: 5 };
    this.fields[5] = figure5.image;
    this.cfigures.push(figure5);

    let figure6 = { id: "knight_black1", image: "../../assets/knight_black.png", position: 6 };
    this.fields[6] = figure6.image;
    this.cfigures.push(figure6);

    let figure7 = { id: "rook_black1", image: "../../assets/rook_black.png", position: 7 };
    this.fields[7] = figure7.image;
    this.cfigures.push(figure7);

    for (var i = 8; i < 16; i++) {
      let figure = { id: "pawn_black" + (i - 8), image: "../../assets/pawn_black.png", position: i };
      this.fields[i] = figure.image;
      this.cfigures.push(figure);
    }

    for (var i = 48; i < 56; i++) {
      let figure = { id: "pawn_white" + (i - 48), image: "../../assets/pawn_white.png", position: i };
      this.fields[i] = figure.image;
      this.pfigures.push(figure);
    }

    let figure56 = { id: "rook_white0", image: "../../assets/rook_white.png", position: 56 };
    this.fields[56] = figure56.image;
    this.pfigures.push(figure56);

    let figure57 = { id: "knight_white0", image: "../../assets/knight_white.png", position: 57 };
    this.fields[57] = figure57.image;
    this.pfigures.push(figure57);

    let figure58 = { id: "bishop_white0", image: "../../assets/bishop_white.png", position: 58 };
    this.fields[58] = figure58.image;
    this.pfigures.push(figure58);

    let figure59 = { id: "queen_white0", image: "../../assets/queen_white.png", position: 59 };
    this.fields[59] = figure59.image;
    this.pfigures.push(figure59);

    let figure60 = { id: "king_white0", image: "../../assets/king_white.png", position: 60 };
    this.fields[60] = figure60.image;
    this.pfigures.push(figure60);

    let figure61 = { id: "bishop_white1", image: "../../assets/bishop_white.png", position: 61 };
    this.fields[61] = figure61.image;
    this.pfigures.push(figure61);

    let figure62 = { id: "knight_white1", image: "../../assets/knight_white.png", position: 62 };
    this.fields[62] = figure62.image;
    this.pfigures.push(figure62);

    let figure63 = { id: "rook_white1", image: "../../assets/rook_white.png", position: 63 };
    this.fields[63] = figure63.image;
    this.pfigures.push(figure63);
  }

  clickField(id) {
    id = parseInt(id);
    if (this.clicked1 == -1) {
      this.clicked1 = id;

      for (var i = 0; i < this.pfigures.length; i++) {
        if (this.pfigures[i].position == this.clicked1) {
          //******************************PAWN*****************************
          if (this.pfigures[i].id.substring(0, 10) == "pawn_white") {
            let flag = false;
            let flag1 = false;
            for (var j = 0; j < this.pfigures.length; j++) {
              if (this.pfigures[i].position - 8 == this.pfigures[j].position) {
                flag = true;
              }
              if (this.pfigures[i].position - 16 == this.pfigures[j].position) {
                flag1 = true;
              }
            }

            if (!flag1) {
              if (this.pfigures[i].position >= 48 && this.pfigures[i].position <= 55) {
                this.clickable.push(this.pfigures[i].position - 16);
              }
            }

            if (!flag) {
              this.clickable.push(this.pfigures[i].position - 8);
            }
            //******************************KNIGHT*****************************
          } else if (this.pfigures[i].id.substring(0, 12) == "knight_white") {
            console.log("pos: " + this.pfigures[i].position);
            console.log(id + 1);
            if ((this.pfigures[i].position + 1) % 8 == 1) {
              console.log("==1");
              this.clickable.push(this.pfigures[i].position - 15);
              this.clickable.push(this.pfigures[i].position - 6);
              this.clickable.push(this.pfigures[i].position + 10);
              this.clickable.push(this.pfigures[i].position + 17);
            } else if ((this.pfigures[i].position + 1) % 8 == 2) {
              console.log("==2");
              this.clickable.push(this.pfigures[i].position - 17);
              this.clickable.push(this.pfigures[i].position - 15);
              this.clickable.push(this.pfigures[i].position - 6);
              this.clickable.push(this.pfigures[i].position + 10);
              this.clickable.push(this.pfigures[i].position + 15);
              this.clickable.push(this.pfigures[i].position + 17);
            } else if ((this.pfigures[i].position + 1) % 8 == 7) {
              console.log("==7");
              this.clickable.push(this.pfigures[i].position - 17);
              this.clickable.push(this.pfigures[i].position - 15);
              this.clickable.push(this.pfigures[i].position - 10);
              this.clickable.push(this.pfigures[i].position + 6);
              this.clickable.push(this.pfigures[i].position + 15);
              this.clickable.push(this.pfigures[i].position + 17);
            } else if ((this.pfigures[i].position + 1) % 8 == 0) {
              console.log("==0");
              this.clickable.push(this.pfigures[i].position - 17);
              this.clickable.push(this.pfigures[i].position - 10);
              this.clickable.push(this.pfigures[i].position + 6);
              this.clickable.push(this.pfigures[i].position + 15);
            } else if ((this.pfigures[i].position + 1) % 8 > 2 && (this.pfigures[i].position + 1) % 8 < 7) {
              this.clickable.push(this.pfigures[i].position - 17);
              this.clickable.push(this.pfigures[i].position - 15);
              this.clickable.push(this.pfigures[i].position - 10);
              this.clickable.push(this.pfigures[i].position - 6);
              this.clickable.push(this.pfigures[i].position + 6);
              this.clickable.push(this.pfigures[i].position + 10);
              this.clickable.push(this.pfigures[i].position + 15);
              this.clickable.push(this.pfigures[i].position + 17);
            }
            for (var j = 0; j < this.clickable.length; j++) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == this.clickable[j]) {
                  console.log("splice " + this.clickable[j]);
                  this.clickable.splice(j, 1);

                }
              }
            }
            //******************************ROOK*****************************
          } else if (this.pfigures[i].id.substring(0, 10) == "rook_white") {
            var up = Math.floor(this.pfigures[i].position / 8);
            for (var j = 1; j <= Math.floor(this.pfigures[i].position / 8); j++) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == (this.pfigures[i].position - j * 8)) {
                  up = j;
                  break;
                }
              }
            }
            for (var h = 0; h < up; h++) {
              this.clickable.push(this.pfigures[i].position - h * 8);
            }

            var down = Math.floor(8 - (this.pfigures[i].position / 8) + 1);
            for (var j = Math.floor(8 - (this.pfigures[i].position / 8) + 1); j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == (this.pfigures[i].position + j * 8)) {
                  down = j;
                  break;
                }
              }
            }
            for (var h = 1; h < down; h++) {
              this.clickable.push(this.pfigures[i].position + h * 8);
            }

            var left = Math.floor(this.pfigures[i].position % 8 + 1);
            for (var j = Math.floor(this.pfigures[i].position % 8 + 1); j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == (this.pfigures[i].position - j)) {
                  left = j;
                  break;
                }
              }
            }
            for (var h = 1; h < left; h++) {
              this.clickable.push(this.pfigures[i].position - h);
            }
            var right = Math.floor(8 - (this.pfigures[i].position % 8));
            for (var j = Math.floor(8 - this.pfigures[i].position % 8); j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == (this.pfigures[i].position + j)) {
                  right = j;
                  break;
                }
              }
            }
            for (var h = 1; h < right; h++) {
              this.clickable.push(this.pfigures[i].position + h);
            }
            //******************************BISHOP*****************************
          } else if (this.pfigures[i].id.substring(0, 12) == "bishop_white") {
            var leftUpLoop: number;
            var leftUp: number;
            if (this.pfigures[i].position / 8 <= this.pfigures[i].position % 8) {
              leftUp = this.pfigures[i].position / 8 + 1;
              leftUpLoop = this.pfigures[i].position / 8 + 1;
            } else if (this.pfigures[i].position / 8 > this.pfigures[i].position % 8) {
              leftUp = this.pfigures[i].position % 8 + 1;
              leftUpLoop = this.pfigures[i].position % 8 + 1;
            }
            for (var j = leftUpLoop; j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == this.pfigures[i].position - j * 9) {
                  leftUp = j;
                  break;
                }
              }
            }
            for (var h = 1; h < leftUp; h++) {
              this.clickable.push(this.pfigures[i].position - h * 9);
            }

            var rightUpLoop: number;
            var rightUp: number;
            if (this.pfigures[i].position / 8 <= 8 - (this.pfigures[i].position % 8)) {
              rightUp = this.pfigures[i].position / 8 + 1;
              rightUpLoop = this.pfigures[i].position / 8 + 1;
            } else if (this.pfigures[i].position / 8 > 8 - (this.pfigures[i].position % 8)) {
              rightUp = 8 - (this.pfigures[i].position % 8);
              rightUpLoop = 8 - (this.pfigures[i].position % 8);
            }
            for (var j = rightUpLoop; j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == this.pfigures[i].position - j * 7) {
                  rightUp = j;
                  break;
                }
              }
            }
            for (var h = 1; h < rightUp; h++) {
              this.clickable.push(this.pfigures[i].position - h * 7);
            }

            var leftDownLoop: number;
            var leftDown;
            if (8 - (this.pfigures[i].position / 8) <= this.pfigures[i].position % 8) {
              leftDown = Math.floor(8 - (this.pfigures[i].position / 8) + 1);
              leftDownLoop = Math.floor(8 - (this.pfigures[i].position / 8) + 1);
            } else if (8 - (this.pfigures[i].position / 8) > this.pfigures[i].position % 8) {
              leftDown = Math.floor(this.pfigures[i].position % 8 + 1);
              leftDownLoop = Math.floor(this.pfigures[i].position % 8 + 1);
            }
            for (var j = leftDownLoop; j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == this.pfigures[i].position + j * 7) {
                  leftDown = j;
                  break;
                }
              }
            }
            for (var h = 1; h < leftDown; h++) {
              this.clickable.push(this.pfigures[i].position + h * 7);
            }

            var rightDownLoop: number;
            var rightDown: number;
            if (8 - (this.pfigures[i].position / 8) <= 8 - (this.pfigures[i].position % 8)) {
              rightDown = Math.floor(8 - (this.pfigures[i].position / 8) + 1);
              rightDownLoop = Math.floor(8 - (this.pfigures[i].position / 8) + 1);
            } else if (8 - (this.pfigures[i].position / 8) > 8 - (this.pfigures[i].position % 8)) {
              rightDown = Math.floor(8 - (this.pfigures[i].position % 8));
              rightDownLoop = Math.floor(8 - (this.pfigures[i].position % 8));
            }
            for (var j = rightDownLoop; j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == this.pfigures[i].position + j * 9) {
                  rightDown = j;
                  break;
                }
              }
            }
            for (var h = 1; h < rightDown; h++) {
              this.clickable.push(this.pfigures[i].position + h * 9);
            }
            //******************************KING*****************************
          } else if (this.pfigures[i].id.substring(0, 10) == "king_white") {
            var left = Math.floor(this.pfigures[i].position % 8);
            var right = Math.floor(7 - this.pfigures[i].position % 8);
            var up = Math.floor(this.pfigures[i].position / 8);
            var down = Math.floor(8 - this.pfigures[i].position / 8);
            console.log("left " + left);
            console.log("right " + right);
            console.log("up " + up);
            console.log("down " + down);
            if (left >= 1 && right >= 1 && up >= 1 && down >= 1) {
              this.clickable.push(this.pfigures[i].position - 9);
              this.clickable.push(this.pfigures[i].position - 8);
              this.clickable.push(this.pfigures[i].position - 7);
              this.clickable.push(this.pfigures[i].position - 1);
              this.clickable.push(this.pfigures[i].position + 1);
              this.clickable.push(this.pfigures[i].position + 7);
              this.clickable.push(this.pfigures[i].position + 8);
              this.clickable.push(this.pfigures[i].position + 9);
            } else if (left < 1 && up < 1) {
              this.clickable.push(this.pfigures[i].position + 1);
              this.clickable.push(this.pfigures[i].position + 8);
              this.clickable.push(this.pfigures[i].position + 9);
            } else if (up < 1 && right >= 1) {
              this.clickable.push(this.pfigures[i].position - 1);
              this.clickable.push(this.pfigures[i].position + 1);
              this.clickable.push(this.pfigures[i].position + 7);
              this.clickable.push(this.pfigures[i].position + 8);
              this.clickable.push(this.pfigures[i].position + 9);
            } else if (up < 1 && right < 1) {
              this.clickable.push(this.pfigures[i].position - 1);
              this.clickable.push(this.pfigures[i].position + 7);
              this.clickable.push(this.pfigures[i].position + 8);
            } else if (left < 1 && down >= 1) {
              this.clickable.push(this.pfigures[i].position - 8);
              this.clickable.push(this.pfigures[i].position - 7);
              this.clickable.push(this.pfigures[i].position + 1);
              this.clickable.push(this.pfigures[i].position + 8);
              this.clickable.push(this.pfigures[i].position + 9);
            } else if (right < 1 && down >= 1) {
              this.clickable.push(this.pfigures[i].position - 9);
              this.clickable.push(this.pfigures[i].position - 8);
              this.clickable.push(this.pfigures[i].position - 1);
              this.clickable.push(this.pfigures[i].position + 7);
              this.clickable.push(this.pfigures[i].position + 8);
            } else if (left < 1 && down < 1) {
              this.clickable.push(this.pfigures[i].position - 8);
              this.clickable.push(this.pfigures[i].position - 7);
              this.clickable.push(this.pfigures[i].position + 1);
            } else if (down < 1 && right >= 1) {
              this.clickable.push(this.pfigures[i].position - 9);
              this.clickable.push(this.pfigures[i].position - 8);
              this.clickable.push(this.pfigures[i].position - 7);
              this.clickable.push(this.pfigures[i].position - 1);
              this.clickable.push(this.pfigures[i].position + 1);
            } else if (down < 1 && right < 1) {
              this.clickable.push(this.pfigures[i].position - 9);
              this.clickable.push(this.pfigures[i].position - 8);
              this.clickable.push(this.pfigures[i].position - 1);
            }

            for (var k = 0; k < this.pfigures.length; k++) {
              for (var h = 0; h < this.clickable.length; h++) {
                if (this.pfigures[k].position == this.clickable[h]) {
                  this.clickable.splice(h, 1);
                }
              }
            }
            //*************************QUEEN*********************************
          } else if (this.pfigures[i].id.substring(0, 11) == "queen_white") {
            var up = Math.floor(this.pfigures[i].position / 8);
            for (var j = 1; j <= Math.floor(this.pfigures[i].position / 8); j++) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == (this.pfigures[i].position - j * 8)) {
                  up = j;
                  break;
                }
              }
            }
            for (var h = 0; h < up; h++) {
              this.clickable.push(this.pfigures[i].position - h * 8);
            }

            var down = Math.floor(8 - (this.pfigures[i].position / 8) + 1);
            for (var j = Math.floor(8 - (this.pfigures[i].position / 8) + 1); j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == (this.pfigures[i].position + j * 8)) {
                  down = j;
                  break;
                }
              }
            }
            for (var h = 1; h < down; h++) {
              this.clickable.push(this.pfigures[i].position + h * 8);
            }

            var left = Math.floor(this.pfigures[i].position % 8 + 1);
            for (var j = Math.floor(this.pfigures[i].position % 8 + 1); j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == (this.pfigures[i].position - j)) {
                  left = j;
                  break;
                }
              }
            }
            for (var h = 1; h < left; h++) {
              this.clickable.push(this.pfigures[i].position - h);
            }
            var right = Math.floor(8 - (this.pfigures[i].position % 8));
            for (var j = Math.floor(8 - this.pfigures[i].position % 8); j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == (this.pfigures[i].position + j)) {
                  right = j;
                  break;
                }
              }
            }
            for (var h = 1; h < right; h++) {
              this.clickable.push(this.pfigures[i].position + h);
            }

            var leftUpLoop: number;
            var leftUp: number;
            if (this.pfigures[i].position / 8 <= this.pfigures[i].position % 8) {
              leftUp = this.pfigures[i].position / 8 + 1;
              leftUpLoop = this.pfigures[i].position / 8 + 1;
            } else if (this.pfigures[i].position / 8 > this.pfigures[i].position % 8) {
              leftUp = this.pfigures[i].position % 8 + 1;
              leftUpLoop = this.pfigures[i].position % 8 + 1;
            }
            for (var j = leftUpLoop; j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == this.pfigures[i].position - j * 9) {
                  leftUp = j;
                  break;
                }
              }
            }
            for (var h = 1; h < leftUp; h++) {
              this.clickable.push(this.pfigures[i].position - h * 9);
            }
            //================================================================
            var rightUpLoop: number;
            var rightUp: number;
            if (this.pfigures[i].position / 8 <= 8 - (this.pfigures[i].position % 8)) {
              rightUp = this.pfigures[i].position / 8 + 1;
              rightUpLoop = this.pfigures[i].position / 8 + 1;
            } else if (this.pfigures[i].position / 8 > 8 - (this.pfigures[i].position % 8)) {
              rightUp = 8 - (this.pfigures[i].position % 8);
              rightUpLoop = 8 - (this.pfigures[i].position % 8);
            }
            for (var j = rightUpLoop; j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == this.pfigures[i].position - j * 7) {
                  rightUp = j;
                  break;
                }
              }
            }
            for (var h = 1; h < rightUp; h++) {
              this.clickable.push(this.pfigures[i].position - h * 7);
            }
            //=================================================================
            var leftDownLoop: number;
            var leftDown;
            if (8 - (this.pfigures[i].position / 8) <= this.pfigures[i].position % 8) {
              leftDown = Math.floor(8 - (this.pfigures[i].position / 8) + 1);
              leftDownLoop = Math.floor(8 - (this.pfigures[i].position / 8) + 1);
            } else if (8 - (this.pfigures[i].position / 8) > this.pfigures[i].position % 8) {
              leftDown = Math.floor(this.pfigures[i].position % 8 + 1);
              leftDownLoop = Math.floor(this.pfigures[i].position % 8 + 1);
            }
            for (var j = leftDownLoop; j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == this.pfigures[i].position + j * 7) {
                  leftDown = j;
                  break;
                }
              }
            }
            for (var h = 1; h < leftDown; h++) {
              this.clickable.push(this.pfigures[i].position + h * 7);
            }
            //==================================================================
            var rightDownLoop: number;
            var rightDown: number;
            if (8 - (this.pfigures[i].position / 8) <= 8 - (this.pfigures[i].position % 8)) {
              rightDown = Math.floor(8 - (this.pfigures[i].position / 8) + 1);
              rightDownLoop = Math.floor(8 - (this.pfigures[i].position / 8) + 1);
            } else if (8 - (this.pfigures[i].position / 8) > 8 - (this.pfigures[i].position % 8)) {
              rightDown = Math.floor(8 - (this.pfigures[i].position % 8));
              rightDownLoop = Math.floor(8 - (this.pfigures[i].position % 8));
            }
            for (var j = rightDownLoop; j >= 1; j--) {
              for (var k = 0; k < this.pfigures.length; k++) {
                if (this.pfigures[k].position == this.pfigures[i].position + j * 9) {
                  rightDown = j;
                  break;
                }
              }
            }
            for (var h = 1; h < rightDown; h++) {
              this.clickable.push(this.pfigures[i].position + h * 9);
            }
          }
        }
      }

    } else if (this.clicked2 == -1) {
      for (var i = 0; i < this.clickable.length; i++) {
        if (id == this.clickable[i]) {
          this.clicked2 = id;
        }
      }
      console.log("1: " + this.clicked1);
      console.log("2: " + this.clicked2);
      /* for (var j = 0; j < this.pfigures.length; j++) {
        if (this.pfigures[j].position == this.clicked2) {
          this.clicked2 = -1;
        }
      } */
      if (this.clicked2 != -1) {
        for (var i = 0; i < this.pfigures.length; i++) {
          if (this.pfigures[i].position == this.clicked1) {
            this.fields[this.clicked1] = "../../assets/empty.png";
            this.fields[this.clicked2] = this.pfigures[i].image;
            this.pfigures[i].position = this.clicked2;
          }
        }

        var moveFigureMsg = "{\"performative\":\"inform\","
        + " \"sender\":" + this.sender + ","
        + " \"receivers\":[" + this.receiver + "],"
        + " \"replyTo\":" + null + ","
        + " \"content\":\""+this.clicked1+"-"+this.clicked2+"\","
        + " \"language\":\" \","
        + " \"encoding\":\" \","
        + " \"ontology\":\" \","
        + " \"protocol\":\" \","
        + " \"conversationId\":\" \","
        + " \"replyWith\":\" \","
        + " \"inReplyTo\":\" \","
        + " \"replyBy\":\" \"}";
        this.service.sendACLMessage(moveFigureMsg).subscribe(res => console.log(res));
      }
      console.log(moveFigureMsg);
      this.clicked1 = -1;
      this.clicked2 = -1;
      console.log("restarted: " + this.clicked1 + " " + this.clicked2);
      this.clickable = [];
    }
  }

  isAvailable(field) {
    for (var i = 0; i < this.clickable.length; i++) {
      if (this.clickable[i] == field) {
        return true;
      }
    }
    return false;
  }
}
