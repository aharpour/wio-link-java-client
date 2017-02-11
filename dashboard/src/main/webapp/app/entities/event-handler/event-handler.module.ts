import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DashboardSharedModule } from '../../shared';

import {
    EventHandlerService,
    EventHandlerPopupService,
    EventHandlerComponent,
    EventHandlerDetailComponent,
    EventHandlerDialogComponent,
    EventHandlerPopupComponent,
    EventHandlerDeletePopupComponent,
    EventHandlerDeleteDialogComponent,
    eventHandlerRoute,
    eventHandlerPopupRoute,
} from './';

let ENTITY_STATES = [
    ...eventHandlerRoute,
    ...eventHandlerPopupRoute,
];

@NgModule({
    imports: [
        DashboardSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EventHandlerComponent,
        EventHandlerDetailComponent,
        EventHandlerDialogComponent,
        EventHandlerDeleteDialogComponent,
        EventHandlerPopupComponent,
        EventHandlerDeletePopupComponent,
    ],
    entryComponents: [
        EventHandlerComponent,
        EventHandlerDialogComponent,
        EventHandlerPopupComponent,
        EventHandlerDeleteDialogComponent,
        EventHandlerDeletePopupComponent,
    ],
    providers: [
        EventHandlerService,
        EventHandlerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardEventHandlerModule {}
