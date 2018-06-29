import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { ChessComponent } from './components/chess/chess.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AgentsComponent } from './components/agents/agents.component';
import { RestService } from './services/rest.service';
import { WebsocketService } from './services/websocket.service';

const appRoutes : Routes = [
  { path: '', component: AgentsComponent },
  { path: 'agents', component: AgentsComponent },
  { path: 'chess', component: ChessComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    ChessComponent,
    NavbarComponent,
    AgentsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    RestService,
    WebsocketService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
