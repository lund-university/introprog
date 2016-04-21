object Main extends App {

  import StringExtras._
  
  
  // Check which dir we are in and if parent to plan then fix prefix
  lazy val here = ".".toPath.toAbsolutePath.getParent
  lazy val filesHere = here.toFile.list.toVector
  lazy val isPlanParent = filesHere.contains("plan")  
  lazy val currentDir = if (isPlanParent) "plan/" else "" 
  
  lazy val texUtf = "%!TEX encoding = UTF-8 Unicode\n"
  
  println("*** plan generation started in: " + here)
  
  object weekPlan extends Plan with Table {
    override val heading = 
      Seq("W", "Datum", "Lp V", "Modul", "Förel", "Övn", "Lab")
  }
  
  object modulePlan extends Plan with Table {
    override val heading = Seq("W", "Modul", "Innehåll")
  }
  
  object overview extends Plan with Table {
    override val heading = Seq("W", "Modul", "Övn", "Lab")
  }
  
 
  weekPlan.  toMarkdown.save(currentDir + "week-plan-generated.md")
  weekPlan.  toHtml    .save(currentDir + "week-plan-generated.html")
  weekPlan.  toLatex   .prepend(texUtf).save(currentDir + "week-plan-generated.tex")
  modulePlan.toMarkdown.save(currentDir + "module-plan-generated.md")
  modulePlan.toHtml    .save(currentDir + "module-plan-generated.html")
  overview  .toLatex   .prepend(texUtf).save(currentDir + "overview-generated.tex")
  
  val weeks = (0 to 6) ++ (8 to 14) //exlude exam weeks
  
  // *** Generate chapter heads with topics of each module
  val conceptBegin = "Koncept du ska lära dig denna vecka:\n" +
    """\begin{multicols}{2}\begin{itemize}[nosep,label={$\square$},leftmargin=*]""" + "\n"
  val conceptEnd   = """\end{itemize}\end{multicols}""" + "\n"
  for (w <- weeks) {
    def toLatexItem(s: String) = s"\\item ${s.trim}\n"
    val label      = "\\label{chapter:" + modulePlan.column("W")(w) + "}"
    val chapter    = "\\chapter{" + modulePlan.column("Modul")(w) + s"}$label\n"
    val concepts   = modulePlan.column("Innehåll")(w).split(',').toVector
    val items      = concepts.map(toLatexItem).mkString.trim 
    val result     = chapter + conceptBegin + items + conceptEnd
    val weekName   = modulePlan.column("W")(w).toLowerCase
    val fileName   = s"../compendium/generated/$weekName-chaphead-generated.tex"
    result.latexEscape.prepend(texUtf).save(currentDir+fileName)
  }
  
  // *** Generate table body rows of progress protocoll in compendium prechapters
  def exerciseRow(s: String) = s"""\\ExeRow{$s}""" 
  def labRow(s: String) = s"""\\LabRow{$s}""" 
  def row(col: String) = weeks.map(weekPlan.column(col)(_)).filterNot(_ == "--")
  val labs = row("Lab").map(labRow).mkString("\n")
  labs.prepend(texUtf).save(currentDir+"../compendium/generated/labs-generated.tex")
  val exercises = 
        row("Övn").
        filterNot(Set("Uppsamling","Extenta").
        contains(_)).
        map(exerciseRow).mkString("\n")
  exercises.prepend(texUtf).save(currentDir + "../compendium/generated/exercises-generated.tex")

  // *** Generate latex commands for lab and exercise names
  val weekNumAlpha =  //as latex cannot have numbers in command names AARGH!!
    Vector("ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN", "",
           "EIGHT","NINE","TEN","ELEVEN","TWELVE","THIRTEEN","FOURTEEN", "")
  def nameDefRow(week: Int, labName: String, exeName: String) =
    s"""\\newcommand{\\ExeWeek${weekNumAlpha(week)}}{$exeName}\n""" +
    s"""\\newcommand{\\LabWeek${weekNumAlpha(week)}}{$labName}\n""" 

  def namesOfWeek(w: Int) = 
    nameDefRow(w, weekPlan.column("Lab")(w), weekPlan.column("Övn")(w))
  val nameDefs = (for (w <- weeks) yield namesOfWeek(w)).mkString("\n")
  nameDefs.prepend(texUtf).save(currentDir + "../compendium/generated/names-generated.tex")

  /*
  // *** Generate template files if not exists -- this is only needed once in the begining...
  def isExistingPath(path: String) = {
    import java.nio.file.{Paths, Files}
    Files.exists(Paths.get(path))
  }
  
  def createFilesOfWeek(week: Int): Unit = {
    val weekNum = weekPlan.column("W")(week).drop(1)
    val templateChap = s"""
      |%!TEX root = ../compendium.tex
      |
      |\\input{generated/w$weekNum-chaphead-generated.tex}
    """.stripMargin
    
    val templateExe = s"""
      |%!TEX root = ../compendium.tex
      |
      |\\Exercise{\\ExeWeek${weekNumAlpha(week)}}
      |
      |\\begin{Goals}
      |\\item 
      |\\end{Goals}
      |
      |\\begin{Preparations}
      |\\item 
      |\\end{Preparations}
      |
      |\\BasicTasks %%%%%%%%%%%%%%%%
      |
      |\\Task 
      |
      |\\Subtask 
      |
      |\\ExtraTasks %%%%%%%%%%%%%%%%%%%
      |
      |\\Task 
      |
      |\\AdvancedTasks %%%%%%%%%%%%%%%%%
      |
      |\\Task     
    """.stripMargin
    
    val templateLab = s"""
      |%!TEX root = ../compendium.tex
      |
      |\\Lab{\\LabWeek${weekNumAlpha(week)}}
      |
      |\\begin{Goals}
      |\\item Att lära sig.
      |\\end{Goals}
      |
      |\\begin{Preparations}
      |\\item Att göra.
      |\\end{Preparations}
      |
      |\\subsection{Obligatoriska uppgifter}
      |
      |\\Task En labbuppgiftsbeskrivning.
      |
      |\\Subtask En underuppgift.
      |
      |\\Subtask En underuppgift.
      |
      |\\subsection{Frivilliga extrauppgifter}
      |
      |\\Task En labbuppgiftsbeskrivning.
      |
      |\\Subtask En underuppgift.
      |
      |\\Subtask En underuppgift.
    """.stripMargin

    val templateSol = s"""
      |%!TEX root = ../compendium.tex
      |
      |\\ExerciseSolution{\\ExeWeek${weekNumAlpha(week)}}
      |
      |\\BasicTasks %%%%%%%%%%%
      |
      |\\Task 
      |
      |\\Subtask \\code{42}
      |
      |\\Subtask Lösningstext.
      |
      |
      |\\ExtraTasks %%%%%%%%%%%%
      |
      |\\Task 
      |
      |\\Subtask \\code{42}
      |
      |\\Subtask Lösningstext.
      |
      |
      |\\AdvancedTasks %%%%%%%%%
      |
      |\\Task 
      |
      |\\Subtask \\code{42}
      |
      |\\Subtask Lösningstext.
    """.stripMargin
        
    val prefix = "../compendium/modules"
    val (chap, exe, lab, sol) = (
      s"$prefix/w$weekNum-chapter.tex",
      s"$prefix/w$weekNum-exercise.tex",
      s"$prefix/w$weekNum-lab.tex",
      s"$prefix/w$weekNum-solutions.tex"
    )
    def create(file: String, data: String, isNonEmpty: Boolean): Unit = { 
      val fileName = currentDir + file
      if (!isExistingPath(fileName)) {  // make sure not to overwrite a file
        if (isNonEmpty) 
          data.prepend(texUtf).save(fileName) 
        else 
          "%%% EMPTY".prepend(texUtf).save(fileName)
      } else println("ALERT!!! NOTHING SAVED -- FILE EXISTS: " + fileName) 
    }
    create(chap,templateChap, true)
    create(exe, templateExe, weekPlan.exerciseNumOfWeek(week).startsWith("Ö"))
    create(lab, templateLab, weekPlan.labNumOfWeek(week).startsWith("L"))
    create(sol, templateSol, weekPlan.exerciseNumOfWeek(week).startsWith("Ö"))  
  }
  
  weeks.drop(2).foreach(createFilesOfWeek)  //make sure not to overwrite files... 
  */
  
}




