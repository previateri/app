import { Injectable, bind, Inject } from '@angular/core';
import { AngularFire, FirebaseListObservable } from 'angularfire2';

import { ITipo, Tipo } from '../';
import { FIREBASE_CONFIG, FirebaseConfig } from '../config';

import { ExceptionService } from '../providers/exception.service';
import { ProgressBarService } from '../providers/progress-bar.service';

@Injectable()
export class TipoPontoInteresseService {

  list: FirebaseListObservable<any>;

  constructor(
    private _angularFire: AngularFire,
    private _exceptionService: ExceptionService,
    private _progressBarService: ProgressBarService,
    @Inject(FIREBASE_CONFIG) _firebaseConfig: FirebaseConfig) {
    this.list = _angularFire.database.list(_firebaseConfig.tipo_ponto_interesse);
    // this.list.subscribe(data => { console.log('TipoPontoInteresseService: ' + JSON.stringify(data)); });
  }

  create(tipo: Tipo): any {
    // console.log('create>> tipo: ' + JSON.stringify(tipo));
    return this.list.push(tipo).key;
  }

  update(tipo: ITipo, changes: any): firebase.Promise<any> {
    // console.log('update>> tipo: ' + JSON.stringify(tipo) + '  changes: ' + JSON.stringify(changes));
    this._progressBarService.show();
    return this.list.update(tipo.$key, changes)
      .then((data) => {
        return this.requestResponse(true, null);
      })
      .catch((error) => {
        return this.requestResponse(false, error);
      });
  }

  remove(tipo: ITipo): firebase.Promise<any> {
    // console.log('remove>> tipo: ' + JSON.stringify(tipo));
    this._progressBarService.show();
    return this.list.remove(tipo.$key)
      .then((data) => {
        return this.requestResponse(true, null);
      })
      .catch((error) => {
        return this.requestResponse(false, error);
      });
  }

  private requestResponse(ok: boolean, error: any): void {
    this._progressBarService.hide();
    if (!ok) {
        this._exceptionService.catchBadResponse(error);
    }
  }

}

export var tipoPontoInteresseServiceInjectables: Array<any> = [
  bind(TipoPontoInteresseService).toClass(TipoPontoInteresseService)
];
