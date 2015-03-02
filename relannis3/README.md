![SaltNPepper project](./gh-site/img/SaltNPepper_logo2010.png)
# pepperModules-RelANNISModules
This project provides an exporter to support the relANNIS format for the linguistic converter framework Pepper (see https://u.hu-berlin.de/saltnpepper). relANNIS is the format used by the Search and Visualization Tool [ANNIS] (https://annis-tools.org/). A detailed description of the exporter can be found in section [RelANNISExporter](#details).

Pepper is a pluggable framework to convert a variety of linguistic formats (like [TigerXML](http://www.ims.uni-stuttgart.de/forschung/ressourcen/werkzeuge/TIGERSearch/doc/html/TigerXML.html), the [EXMARaLDA format](http://www.exmaralda.org/), [PAULA](http://www.sfb632.uni-potsdam.de/paula.html) etc.) into each other. Furthermore Pepper uses Salt (see https://github.com/korpling/salt), the graph-based meta model for linguistic data, which acts as an intermediate model to reduce the number of mappings to be implemented. That means converting data from a format _A_ to format _B_ consists of two steps. First the data is mapped from format _A_ to Salt and second from Salt to format _B_. This detour reduces the number of Pepper modules from _n<sup>2</sup>-n_ (in the case of a direct mapping) to _2n_ to handle a number of n formats.

![n:n mappings via SaltNPepper](./gh-site/img/puzzle.png)

In Pepper there are three different types of modules:
* importers (to map a format _A_ to a Salt model)
* manipulators (to map a Salt model to a Salt model, e.g. to add additional annotations, to rename things to merge data etc.)
* exporters (to map a Salt model to a format _B_).

For a simple Pepper workflow you need at least one importer and one exporter.

## Requirements
Since the here provided module is a plugin for Pepper, you need an instance of the Pepper framework. If you do not already have a running Pepper instance, click on the link below and download the latest stable version (not a SNAPSHOT):

> Note:
> Pepper is a Java based program, therefore you need to have at least Java 7 (JRE or JDK) on your system. You can download Java from https://www.oracle.com/java/index.html or http://openjdk.java.net/ .


## Install module
If this Pepper module is not yet contained in your Pepper distribution, you can easily install it. Just open a command line and enter one of the following program calls:

**Windows**
```
pepperStart.bat 
```

**Linux/Unix**
```
bash pepperStart.sh 
```

Then type in command *is* and the path from where to install the module:
```
pepper> update de.hu_berlin.german.korpling.saltnpepper::pepperModules-pepperModules-RelANNISModules::https://korpling.german.hu-berlin.de/maven2/
```

## Usage
To use this module in your Pepper workflow, put the following lines into the workflow description file. Note the fixed order of xml elements in the workflow description file: &lt;importer/>, &lt;manipulator/>, &lt;exporter/>. The RelANNISExporter is an importer module, which can be addressed by one of the following alternatives.
A detailed description of the Pepper workflow can be found on the [Pepper project site](https://u.hu-berlin.de/saltnpepper). 

### a) Identify the module by name

```xml
<importer name="RelANNISExporter" path="PATH_TO_CORPUS"/>
```

### b) Identify the module by formats
```xml
<importer formatName="xml" formatVersion="1.0" path="PATH_TO_CORPUS"/>
```

### c) Use properties
```xml
<importer name="RelANNISExporter" path="PATH_TO_CORPUS">
  <property key="PROPERTY_NAME">PROPERTY_VALUE</key>
</importer>
```

## Contribute
Since this Pepper module is under a free license, please feel free to fork it from github and improve the module. If you even think that others can benefit from your improvements, don't hesitate to make a pull request, so that your changes can be merged.
If you have found any bugs, or have some feature request, please open an issue on github. If you need any help, please write an e-mail to saltnpepper@lists.hu-berlin.de .

## Funders
This project has been funded by the [department of corpus linguistics and morphology](https://www.linguistik.hu-berlin.de/institut/professuren/korpuslinguistik/) of the Humboldt-Universität zu Berlin, the Institut national de recherche en informatique et en automatique ([INRIA](www.inria.fr/en/)) and the [Sonderforschungsbereich 632](https://www.sfb632.uni-potsdam.de/en/). 

## License
  Copyright 2009 Humboldt-Universität zu Berlin, INRIA.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.


# <a name="details">RelANNISExporter</a>

The RelANNISExporter make use of the relANNISModel project to map data from the relANNIS
model to the Salt model. That means, the conversion process is divided into two steps:
* Mapping of the Salt model to a relANNIS model
* Exporting the relANNIS model to a file
Since relANNIS is available in two different versions, the relANNIS 3.1 format and the relANNIS
3.2 format, you can customize the relANNIS creation only when using the format description. That
means the second case of identifying an im- or exporter. The relANNIS 3.1 format fits for all ANNIS
2.X versions, whereas the relANNIS 3.2 is importable since ANNIS 3.0.

The relANNIS format is a csv based format and uses for separating columns the tab character
and for separating lines a line break character. Therefore it is necessary to not have tab characters
or line breaks inside the data like annotation names, annotation values, primary data and so on.
Of course, the data could be checked when exporting them. But since checking each String for
occurances of these characxters is a time consuming job and a lot of data does not contain such
characters, it is up to the user to switch one the that cleaning process. Therefore the two properties
relANNIS.exporter.tab_escape and relANNIS.exporter.linebreak_escape exist, with which even the
replacement character or character sequence can be determined.

## Properties

The following table contains an overview of all usable
properties to customize the behaviour of this Pepper module. The following section contains a close
description to each single property and describes the resulting differences in the mapping to the Salt
model.

|Name of property                  |Type of property |optional/mandatory |default value|
|----------------------------------|-----------------|-------------------|-------------| 
|relANNIS.exporter.tab_escape      |String           | optional          |--           |
|relANNIS.exporter.linebreak_escape|String           |optional           |--           |
|relANNIS.exporter.backslash_escape|String           |optional           |--           |

### relANNIS.exporter.tab_escape

With this property tab characters ('\t') will be replaces by the given character or character sequence.
For instance imagine the following text:
```
This\tis\ta\ttext
```
Normally, in most editors, a tab is shown as a longer whitespace and not as '\t'. The use of the property:

### relANNIS.exporter.tab_escape=BLANK

will result in the following text:
```
ThisBLANKisBLANKaBLANKtext
```
Of course, this replacement does not make much sense and normally you would replace a tab with one
or a sequence of blanks. This is possible when using the blank key instead of the 'BLANK' sequence,
but hard to read in this example.

### relANNIS.exporter.linebreak_escape

With this property linebreak characters (like '\n' and '\r') will be replaces by the given character or
character sequence. For instance imagine the following text:

```
This\nis\na\ntext
```

Normally, in most editors, a linebreak is not shown and not as '\t'. The use of the property:

```
relANNIS.exporter.linebreak_escape=BLANK
```

will result in the following text:

```
ThisBLANKisBLANKaBLANKtext
```

Of course, this replacement does not make much sense and normally you would replace a linebreak
with one or a sequence of blanks. This is possible when using the blank key instead of the 'BLANK'
sequence, but hard to read in this example.

```
relANNIS.exporter.backslash_escape
```

With this property bakcslash character ('\') will be replaces by the given character or character
sequence. For instance imagine the following text:

```
This\is\a\text
```

. The use of the property:

```
relANNIS.exporter.backslash_escape=BLANK
```

will result in the following text:
```
ThisBLANKisBLANKaBLANKtext
```
