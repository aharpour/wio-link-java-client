import { Node } from '../node';
export class Grove {
    constructor(
        public id?: number,
        public name?: string,
        public type?: string,
        public passive?: boolean,
        public node?: Node,
    ) { }
}
