import { Node } from '../node';
import { TaskHandler } from '../task-handler';
import { EventHandler } from '../event-handler';
export class Task {
    constructor(
        public id?: number,
        public name?: string,
        public period?: number,
        public forceSleep?: boolean,
        public keepAwake?: boolean,
        public node?: Node,
        public taskHandler?: TaskHandler,
        public eventHandler?: EventHandler,
    ) { }
}
