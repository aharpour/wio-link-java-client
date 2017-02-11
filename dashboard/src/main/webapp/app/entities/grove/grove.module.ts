import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DashboardSharedModule } from '../../shared';

import {
    GroveService,
    GrovePopupService,
    GroveComponent,
    GroveDetailComponent,
    GroveDialogComponent,
    GrovePopupComponent,
    GroveDeletePopupComponent,
    GroveDeleteDialogComponent,
    groveRoute,
    grovePopupRoute,
} from './';

let ENTITY_STATES = [
    ...groveRoute,
    ...grovePopupRoute,
];

@NgModule({
    imports: [
        DashboardSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GroveComponent,
        GroveDetailComponent,
        GroveDialogComponent,
        GroveDeleteDialogComponent,
        GrovePopupComponent,
        GroveDeletePopupComponent,
    ],
    entryComponents: [
        GroveComponent,
        GroveDialogComponent,
        GrovePopupComponent,
        GroveDeleteDialogComponent,
        GroveDeletePopupComponent,
    ],
    providers: [
        GroveService,
        GrovePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardGroveModule {}
