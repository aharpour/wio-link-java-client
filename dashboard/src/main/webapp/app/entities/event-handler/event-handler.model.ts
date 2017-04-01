const enum Langauge {
    'GROOVYSCRIPT',
    'JAVASCRIPT',
    'JAVA'
};
export class EventHandler {
    constructor(
        public id?: number,
        public name?: string,
        public langauge?: Langauge,
        public code?: any,
    ) { }
}
