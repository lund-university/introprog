# lunduniversity/introprog

[![Build Status](https://travis-ci.org/lunduniversity/introprog.svg?branch=master)](https://travis-ci.org/lunduniversity/introprog)

This is the repo of a course given by Lund University called "Introduction to Programming" using Scala and Java. The repo contains course material in Swedish and some English along with code examples and libraries used in exercises and labs.

Course homepage (in Swedish): http://cs.lth.se/pgk/ 

This is work in progress; the first instance of the course starts this fall semester, August 29, 2016 at LTH in Lund.

## How to use this repo

* Download the latest version of the [compendium](https://github.com/lunduniversity/introprog/raw/master/compendium/compendium.pdf).

* Download the [workspace](https://github.com/lunduniversity/introprog/blob/master/lib/workspace.zip) to be used with your favorite code editor and IDE. See instructions in appendices in the compendium.

## Contents of this repo

The main directories are:
* `compendium` with the course teaching material including lecture notes, exercises, labs, etc.
  * `modules` with lectures exercises and labs for each week
  * `generated` with output from execution of `plan/Main.scala` included in the compendium
* `slides` with lecture notes in project friendly format
* `workspace` with student workspace including lab code skeletons, examples, code libs etc. 
* `plan` with module contents and concepts per week
* `img` images used in compendium and slides
* `refs` extra readings, background material
* `teachers` information for teachers

## How to build this repo

### With sbt

If you like to use the [scala build tool, sbt](http://www.scala-sbt.org) it is easy to build everything in this repo:

* Install sbt: http://www.scala-sbt.org/release/docs/Setup.html

* Download this repo: https://github.com/lunduniversity/introprog/archive/master.zip (or make a fork and then a clone as explained [below](https://github.com/lunduniversity/introprog#how-to-contribute-to-this-repo))

* Unpack the zip in some suitable directory named introprog.

* run these sbt commands in a terminal window in the introprog directory:
  * `sbt compile` to compile all sources 
  * `sbt eclipse` to make eclipse project files 
  * `sbt gen` to generate the planning files, alias for `sbt plan/run`
  * `sbt pdf` to make slides and compendium using pdflatex
  * `sbt build` run both the commands `gen` and `pdf` in sequence

### Without sbt

* **Workspace** You can import the different sub projects in the `workspace` directory into your favorite IDE, e.g. Eclipse or IntelliJ, and build them there after importing each of them from within the IDE.

* **Plan** You can compile and run the Main.scala object in the `plan` directory using the command `scala Main` in terminal after you have manually compiled all `.scala` files.

* **Latex** You can build the `.tex` files to `.pdf` with `pdflatex` in terminal or using your favourite Latex editor, e.g. texworks.



## How to contribute to this repo

### Fork and clone

* Learn the basics about git, especially the "Getting Started" and "Git Basics" sections in this book: https://git-scm.com/book/en/v2 

* Get an account at github if you don't have one already. Recommended user name if in doubt: `firstnamefamilyname` with no capital letters and no hyphens.

* Install git: https://git-scm.com/book/en/v2/Getting-Started-Installing-Git

* Make a **fork** of lunduniversity/introprog in GitHub to your own GitHub account: https://help.github.com/articles/fork-a-repo/ 

* **Clone** your fork to your local computer: https://help.github.com/articles/cloning-a-repository/ 

### Keeping your fork in synch

* If you install the GitHub client (avaliable for Win and Mac but not Linux) called "GitHub desktop" https://desktop.github.com/ you can keep your fork in synch with the upstream repo by a single click in the GUI.

* Otherwise, this is how to pull changes from upstream to your fork with git commands: https://help.github.com/articles/syncing-a-fork/ 

### Making contributions

* If you find a typo or minor issue that is straight-forward to fix you are very welcome to create a pull request directly as explained below. But if your contribution is more significant you should open an issue first and start a discussion about your proposal. In the latter case, click the issue tab at the top of this page.

* Before you change locally, make sure your fork is in synch (see above). Frequently do `git pull` or press the synch button in the GitHub desktop GUI.

* You must check that your fix compiles (to Latex or bytecode) before you commit.

* Whenever you are ready with an incremental change, do `git commit -m "msg"` and then `git push`, or commit in the GUI and press the synch button. Think carefully about your commit message, as discussed in the next section.

* When you are ready with a contribution that is good enough to be incorporated in upstream, then create a pull request: https://help.github.com/articles/creating-a-pull-request/

* Keep your pull requests minimal and coherent to create a small change sets that will be easy to merge as a single unit. Don't pack a lot of unrelated changes in the same pull request. Take a look [here](https://github.com/lunduniversity/introprog/pulls?q=is%3Apr+is%3Aclosed) for examples of previously accepted pull requests.

* Don't include pdf:s or binaries in the pull request. The maintainers will recompile the repo after your pull request has been merged. You can then checkout your pdf:s before you synch with upstream.


### Writing commit messages

* Write concise and informative [commit messages](http://chris.beams.io/posts/git-commit/) that explains why the commit was made. 
* Start each commit message with a direct verb, preferably one of the following:
  * `add` when you have created new stuff that was not there before
  * `update` when you have changed existing stuff
  * `fix` when you have corrected a bug or fixed a typo etc.
  * `remove` when you have removed stuff
  * `rename` when you have renamed files or other stuff without changing appearance/meaning
  * `refactor` when you have changed things structurally but not changed actual appearance/meaning
* Example of commit messages
  * `git commit -am "update exercise w03 to improve explanation"`
  * `git commit -am "add task in exercises w05 vector copy"`
* Make small commits and commit often. Try to keep commits atomic and only within one file if meaningful.
* Make sure your change compiles before committing. Do *not* push code that does not compile!


### Coding style 

When learning how to program it is more important to write *something* and start experimenting in a playful way, than to forcefully adhere to a particular coding standard; but students should also (eventually) understand the benefits of having a coding standard. 

In this course we pragmatically follow these style guides: 

* Scala style:
  * The Scala style guide: http://docs.scala-lang.org/style/ 
  * A Scala best practice guide: https://github.com/alexandru/scala-best-practices
* Java style:
  * http://www.oracle.com/technetwork/java/codeconventions-150003.pdf

When you make contributions to code in this repo and when you review pull-requests, check that the contributions follow the above guidelines pragmatically. In particular, **lab assigments stubs** and **answers to exercises** should, if there are no special reasons not to, follow the above style guides.

Here are some other inspiring style guides that illustrate the variety in what different organisations impose:
* Scala:
  * http://twitter.github.io/effectivescala/
  * https://github.com/databricks/scala-style-guide
  * https://github.com/paypal/scala-style-guide
* Java:
  * https://google.github.io/styleguide/javaguide.html
  * https://wiki.eclipse.org/Coding_Conventions 
  * https://www.securecoding.cert.org/confluence/display/java/Java+Coding+Guidelines

### Latex guide 

* Make sure you have your TeX editor set to UTF-8 encoding. If you get strange errors in relation to Swedish characters, this is likely due to problems relating to non-UTF-8 encodings on Mac or Windows. Linux usually works out-of-the-box.


* Install [texlive-full](https://www.tug.org/texlive/) to get all extra latex stuff that is needed to compile the tex code in this repo. If you don't know which tex editor to use, try [texworks](https://www.tug.org/texworks/).

* For Mac OSX users: there are some problems with El-Capitan and TeX. For some users there are problems compiling .tex-files in the terminal out of the box. You may get this error message: 'mktexpk: No such file or directory' or similar. Using TeXShop to compile the document seems to resolve the issue. To configure TeXShop correctly on El-Capitan, follow the guide https://www.tug.org/mactex/UpdatingForElCapitan.pdf

* Check out the `.cls` files in `compendium/` and `slides/` that provide many useful latex commands.
  * [compendium.cls](https://github.com/lunduniversity/introprog/blob/master/compendium/compendium.cls)
  * [lecturenotes.cls](https://github.com/lunduniversity/introprog/blob/master/slides/lecturenotes.cls)

* Check out some similar, already written `.tex` document and compare with the compiled `.pdf` to see the commands and conventions we use. 

* Some custom latex commands in our .cls files: 
  * `\begin{Code}` ... `\end{Code}` and `\scalainputlisting{examples/hello-app.scala}` are used for Scala code 
  * `\begin{Code}[language=Java]` ... `\end{Code}` and `\javainputlisting{examples/Hi.java}` are used for Java code
  * `\begin{Slide}` and `\end{Slide}` defined in `slides/lecture-notes.cls` and in `compendium/compendium.cls` is used to generate beamer slides and to generate framed text in compendium chapters together with lecture notes that appear after each slide.
  
#### How to make figures in the compendium

Here is an example of our convention for figures (using the float-package):

        \begin{figure}[H]
        \centering
        \includegraphics[width=0.7\textwidth]{../img/pirates/selectws.png}
        \caption { \emph{Öppna workspace.} Bläddra fram till kursens workspace och klicka OK. }
        \label{fig:eclipse:ide:open}
        \end{figure}

Adapt the size of the figure width to make it look good by changing 0.7 above to something appropriate depending on the proportion of height/width of your figure.

Labels are named with the convention `\label{chap:subchap:subsubchap:somegoodname}`
 
# License

Copyright &copy; 2015-2016. Dept. of Computer Science at Lund University, Lund, Sweden.

Contributors: https://github.com/lunduniversity/introprog/blob/master/contributors.tex

This work is licensed under a
Creative Commons Attribution-ShareAlike 4.0 International License.


You are free to:

* Share — copy and redistribute the material in any medium or format
* Adapt — remix, transform, and build upon the materia for any purpose, even commercially.
* The licensor cannot revoke these freedoms as long as you follow the license terms.

Under the following terms:

* Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
*  ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
* No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.


See http://creativecommons.org/licenses/by-sa/4.0/

