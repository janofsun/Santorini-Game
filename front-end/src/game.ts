interface Cells {
  cells: Cell[]
  template: HandlebarsTemplateDelegate<any>
  instructions: String
}

interface Cell {
  textPlayer: String
  textLevel: String
  link: String
  godCard: String
}

export type { Cells, Cell }
