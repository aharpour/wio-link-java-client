import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DashboardSharedModule } from '../../shared';

import {
    TaskHandlerService,
    TaskHandlerPopupService,
    TaskHandlerComponent,
    TaskHandlerDetailComponent,
    TaskHandlerDialogComponent,
    TaskHandlerPopupComponent,
    TaskHandlerDeletePopupComponent,
    TaskHandlerDeleteDialogComponent,
    taskHandlerRoute,
    taskHandlerPopupRoute,
} from './';

let ENTITY_STATES = [
    ...taskHandlerRoute,
    ...taskHandlerPopupRoute,
];

@NgModule({
    imports: [
        DashboardSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TaskHandlerComponent,
        TaskHandlerDetailComponent,
        TaskHandlerDialogComponent,
        TaskHandlerDeleteDialogComponent,
        TaskHandlerPopupComponent,
        TaskHandlerDeletePopupComponent,
    ],
    entryComponents: [
        TaskHandlerComponent,
        TaskHandlerDialogComponent,
        TaskHandlerPopupComponent,
        TaskHandlerDeleteDialogComponent,
        TaskHandlerDeletePopupComponent,
    ],
    providers: [
        TaskHandlerService,
        TaskHandlerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardTaskHandlerModule {}
