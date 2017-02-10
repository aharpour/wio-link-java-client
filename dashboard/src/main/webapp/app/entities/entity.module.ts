import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DashboardEventHandlerModule } from './event-handler/event-handler.module';
import { DashboardNodeModule } from './node/node.module';
import { DashboardTaskModule } from './task/task.module';
import { DashboardTaskHandlerModule } from './task-handler/task-handler.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        DashboardEventHandlerModule,
        DashboardNodeModule,
        DashboardTaskModule,
        DashboardTaskHandlerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardEntityModule {}
