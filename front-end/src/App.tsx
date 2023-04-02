import Handlebars from 'handlebars'
import { Component } from 'react'
import { Cells, Cell } from './game'
import './App.css'

var oldHref = 'http://localhost:3000'

class App extends Component<{}, Cells> {
  constructor (props: {}) {
    super(props)
    this.state = {
      cells: [
        { textPlayer: '', textLevel: '', link: '/play?x=0&y=0', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=1&y=0', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=2&y=0', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=3&y=0', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=4&y=0', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=0&y=1', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=1&y=1', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=2&y=1', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=3&y=1', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=4&y=1', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=0&y=2', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=1&y=2', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=2&y=2', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=3&y=2', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=4&y=2', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=0&y=3', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=1&y=3', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=2&y=3', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=3&y=3', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=4&y=3', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=0&y=4', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=1&y=4', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=2&y=4', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=3&y=4', godCard: '' },
        { textPlayer: '', textLevel: '', link: '/play?x=4&y=4', godCard: '' }
      ],
      template: this.loadTemplate(),
      instructions: 'Player 0 Initialization'
    }
  }

  loadTemplate (): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById('handlebars')
    return Handlebars.compile(src?.innerHTML, {})
  }

  createCell (p: any): Cell[] {
    const newCells: Cell[] = []
    for (var i = 0; i < p.cells.length; i++) {
      var c: Cell = {
        textPlayer: p.cells[i].textPlayer,
        textLevel: p.cells[i].textLevel,
        link: p.cells[i].link,
        godCard: p.cells[i].godCard
      }
      newCells.push(c)
    }

    return newCells
  }

  getTurn (p: any): String {
    return p.turn
  }

  getWinner (p: any): String | undefined {
    return p.winner
  }

  getInstr (init: boolean, turn: String, winner: String | undefined, godCard: String): String {
    if (winner !== undefined) return `Player ${turn.valueOf()} wins!`
    else if (!init) return `Player ${turn.valueOf()} Initialization`
    else return `Player ${turn.valueOf()} with ${godCard} : Select a worker to move and build.`
  }

  getPlayersInit (p: any): boolean {
    var cnter = 0
    for (var i = 0; i < p.length; i++) {
      if (p[i].textPlayer === 'X' || p[i].textPlayer === 'O') cnter += 1
    }
    if (cnter === 4) return true
    return false
  }

  getGodCard (p: any, turn: String): String {
    const playerNum = Number(turn)
    const player = playerNum === 1 ? 'O' : 'X'
    for (var i = 0; i < p.length; i++) {
      if (p[i].textPlayer === player) return p[i].godCard
    }
    return ''
  }

  async newGame (): Promise<void> {
    const response = await fetch('newgame')
    const json = await response.json()

    const newCells: Cell[] = this.createCell(json)
    this.setState({
      cells: newCells,
      instructions: 'New Game! Players Initialization.'
    })
  }

  async demeter (): Promise<void> {
    const response = await fetch('demeter')
    const json = await response.json()

    const newCells: Cell[] = this.createCell(json)
    this.setState({
      cells: newCells,
      instructions: 'Demeter God selected.'
    })
  }

  async minotaur (): Promise<void> {
    const response = await fetch('minotaur')
    const json = await response.json()

    const newCells: Cell[] = this.createCell(json)
    this.setState({
      cells: newCells,
      instructions: 'Minotaur God selected.'
    })
  }

  async pan (): Promise<void> {
    const response = await fetch('pan')
    const json = await response.json()

    const newCells: Cell[] = this.createCell(json)
    this.setState({
      cells: newCells,
      instructions: 'Pan God selected.'
    })
  }

  async nogod (): Promise<void> {
    const response = await fetch('nogod')
    const json = await response.json()

    const newCells: Cell[] = this.createCell(json)
    this.setState({
      cells: newCells,
      instructions: 'No God selected.'
    })
  }

  async skip (): Promise<void> {
    const response = await fetch('skip')
    const json = await response.json()

    const turn = this.getTurn(json)
    const newCells: Cell[] = this.createCell(json)
    this.setState({ cells: newCells, instructions: 'Skip the optional action for player ' + turn.valueOf() })
  }

  // async undo (): Promise<void> {
  //   const response = await fetch('undo')
  //   const json = await response.json()

  //   const newCells: Cell[] = this.createCell(json)
  //   const turn = this.getTurn(json)
  //   const winner = this.getWinner(json)
  //   const init = this.getPlayersInit(newCells)
  //   const godCard = this.getGodCard(newCells, turn)
  //   const instr = this.getInstr(init, turn, winner, godCard)
  //   this.setState({ cells: newCells, instructions: instr })
  // }

  async play (url: String): Promise<void> {
    const href = 'play?' + url.split('?')[1]
    const response = await fetch(href)
    const json = await response.json()

    const newCells: Cell[] = this.createCell(json)
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const init = this.getPlayersInit(newCells)
    const godCard = this.getGodCard(newCells, turn)
    const instr = this.getInstr(init, turn, winner, godCard)
    this.setState({ cells: newCells, instructions: instr })
  }

  async switch (): Promise<void> {
    if (
      window.location.href === 'http://localhost:3000/newgame' &&
      oldHref !== window.location.href
    ) {
      this.newGame()
      oldHref = window.location.href
    } else if (
      window.location.href.split('?')[0] === 'http://localhost:3000/play' &&
      oldHref !== window.location.href
    ) {
      this.play(window.location.href)
      oldHref = window.location.href
    } else if (
      window.location.href.split('?')[0] === 'http://localhost:3000/demeter' &&
      oldHref !== window.location.href
    ) {
      this.demeter()
      oldHref = window.location.href
    } else if (
      window.location.href.split('?')[0] === 'http://localhost:3000/minotaur' &&
      oldHref !== window.location.href
    ) {
      this.minotaur()
      oldHref = window.location.href
    } else if (
      window.location.href.split('?')[0] === 'http://localhost:3000/pan' &&
      oldHref !== window.location.href
    ) {
      this.pan()
      oldHref = window.location.href
    } else if (
      window.location.href.split('?')[0] === 'http://localhost:3000/nogod' &&
      oldHref !== window.location.href
    ) {
      this.nogod()
      oldHref = window.location.href
    } else if (
      window.location.href === 'http://localhost:3000/skip' &&
      oldHref !== window.location.href
    ) {
      this.skip()
      oldHref = window.location.href
    }
    // else if (
    //   window.location.href === 'http://localhost:3000/undo' &&
    //   oldHref !== window.location.href
    // ) {
    //   this.undo()
    //   oldHref = window.location.href
    // }
  };

  render (): JSX.Element {
    this.switch()
    return (
      <div className='App'>
        <div
          dangerouslySetInnerHTML={{
            __html: this.state.template({ cells: this.state.cells, instructions: this.state.instructions })
          }}
        />
      </div>
    )
  };
}

export default App
