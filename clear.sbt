// Handy `clear` command:
def clearConsoleCommand = Command.command("clear") { state =>
  val cr = new jline.console.ConsoleReader()
  cr.clearScreen
  state
}

commands += clearConsoleCommand