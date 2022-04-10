export class Project {
  readonly id: string;
  readonly alias: string;
  readonly name: string;
  readonly overview: string;

  constructor(id: string, alias: string, name: string, overview: string) {
    this.id = id;
    this.alias = alias;
    this.name = name;
    this.overview = overview;
  }
}
