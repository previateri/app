import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

import { ValidationService } from '../global/validation.service';

@Component({
    selector: 'control-messages',
    template: `<ion-item *ngIf="errorMessage !== null">
                    <ion-label danger style="font-size: 0.875em;">{{ errorMessage }}</ion-label>
                </ion-item>`
})
export class ControlMessagesComponent {
    @Input() control: FormControl;
    constructor() { }

    get errorMessage() {
        for (let propertyName in this.control.errors) {
            if (this.control.errors.hasOwnProperty(propertyName) && this.control.touched) {
                return ValidationService.getValidatorErrorMessage(propertyName, this.control.errors[propertyName]);
            }
        }

        return null;
    }
}
