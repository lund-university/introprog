  trait Table {
    type Row    = Seq[String]
    type RowMap = Map[String, String]
    type Grid   = Seq[RowMap]
     
    def heading: Row 
    def body   : Grid 
    
    lazy val grid: Grid = body.map(_.withDefaultValue(" "))
    
    def column(head: String): Seq[String] = grid.map(_.apply(head))
    
    lazy val padLimit = 40 // no use padding too wide column

    def padWidth(head: String) = {
      val maxWidth = (head +: grid.map(row => row(head))).map(_.size).max
      if (maxWidth > padLimit) 0 else maxWidth  
    }
    
    def markdownBodyRow(row: RowMap) = 
      heading.map(h => row(h).padTo(padWidth(h), ' ') ).mkString("| "," | "," |")  

    def markdownHeadingSeparatorRow = 
      heading.map(padWidth).map(s => "|:--".padTo(s + 3, '-')).mkString("","","|\n")
    
    lazy val toMarkdown: String = 
      heading.map(h => h.padTo(padWidth(h), ' ')).mkString("| "," | "," |\n") + 
        markdownHeadingSeparatorRow + 
          grid.map(markdownBodyRow).mkString("\n")
    
    lazy val htmlHeadings = 
      heading.map(head => """<th align="left">""" + head + "</th>").
        mkString(s"""<tr class="header">\n""", "\n", "</tr>")
    
    def htmlBodyRow(row: RowMap) = 
      heading.map(h => row(h)).map(x => """<td align="left">""" + x + "</td>").
        mkString(s"""<tr>\n""", "\n", "</tr>")

    
    def toHtml: String =  
      s"""|<table>
          |<thead>
          |${htmlHeadings}
          |</thead>
          |<tbody>
          |${grid.map(htmlBodyRow).mkString("\n")}
          |</tbody>
          |</table>
          |""".stripMargin
          
    lazy val latexHeadings = 
      heading.map(h => s"\\textit{$h}").mkString(""," & "," \\\\ \\hline \\hline")
    
    def latexBodyRowLastcolMulticolumnIfEmpty(row: RowMap) = {
      def multicol(s: String) = s"\\multicolumn{2}{l}{$s}"
      val rs = heading.map(row)
      val rowStrings = if (!rs.endsWith(Seq(""))) rs else 
          rs.dropRight(2) :+ multicol(rs.dropRight(1).lastOption.getOrElse(""))
      rowStrings.mkString(""," & "," \\\\")
    }
          
    //def latexBodyRow(row: RowMap) = heading.map(row).mkString(""," & "," \\\\")

    def latexTableBody: String = 
      grid.map(latexBodyRowLastcolMulticolumnIfEmpty).mkString("\n")      
      // grid.map(latexBodyRow).mkString("\n")
    

    def toLatex: String = 
      s"""|\\begin{tabular}${Seq.fill(heading.size)("l").mkString("{","|","}")}
          |$latexHeadings
          |$latexTableBody
          |\\end{tabular}
          |""".stripMargin
  }
