import { Component, OnInit } from '@angular/core';
import { Figure } from '../../interfaces/figure';

@Component({
  selector: 'app-chess',
  templateUrl: './chess.component.html',
  styleUrls: ['./chess.component.css']
})
export class ChessComponent implements OnInit {

  private pfigures: Figure[] = [];
  private cfigures: Figure[] = [];

  private fields: String[] = [];

  constructor() {
    for (var i = 0; i < 64; i++) {
      this.fields[i] = "../../assets/empty.png";
    }
  }

  ngOnInit() {
  }

  startGame() {

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
}
