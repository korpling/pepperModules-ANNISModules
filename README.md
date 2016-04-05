![SaltNPepper project](./gh-site/img/SaltNPepper_logo2010.png)
# pepperModules-RelANNISModules
This project provides an exporter to support the ANNIS format for the linguistic converter framework Pepper (see https://u.hu-berlin.de/saltnpepper). ANNIS is the format used by the Search and Visualization Tool [ANNIS] (https://annis-tools.org/). A detailed description of the exporter can be found in section [ANNISExporter](#details).

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
pepper> update de.hu_berlin.german.korpling.saltnpepper::pepperModules-pepperModules-ANNISModules::https://korpling.german.hu-berlin.de/maven2/
```

## Usage
To use this module in your Pepper workflow, put the following lines into the workflow description file. Note the fixed order of xml elements in the workflow description file: &lt;importer/>, &lt;manipulator/>, &lt;exporter/>. The RelANNISExporter is an importer module, which can be addressed by one of the following alternatives.
A detailed description of the Pepper workflow can be found on the [Pepper project site](https://u.hu-berlin.de/saltnpepper). 

### a) Identify the module by name

```xml
<importer name="ANNISExporter" path="PATH_TO_CORPUS"/>
```

### b) Identify the module by formats
```xml
<importer formatName="xml" formatVersion="1.0" path="PATH_TO_CORPUS"/>
```

### c) Use properties
```xml
<importer name="ANNISExporter" path="PATH_TO_CORPUS">
  <property key="PROPERTY_NAME">PROPERTY_VALUE</property>
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


# <a name="details">ANNISExporter</a>

The ANNIS format is a CSV based format and uses the tab character for separating columns and the line break character 
for separating lines.

## Properties

The following table contains an overview of all usable
properties to customize the behaviour of this Pepper module. The following section contains a close
description to each single property and describes the resulting differences in the mapping to the Salt
model.

|Name of property                  |Type of property |optional/mandatory |default value|
|----------------------------------|-----------------|-------------------|-------------|
|escapeCharacters                  |Boolean          | optional          |true         |
|escapeCharactersList              |String           | optional          |--           | 
|clobber.visualisation             |Boolean          |                   |true         |
|clobber.corpus_annotation         |Boolean          |                   |true         |
|corpusName                        |String           |                   |--           |
|mergeTextsWithTimeline            |Boolean          |optional           |true         |
|excludeSingleDomType              |Boolean          |optional           |false        |
|mergeVisualisation                |Boolean          |optional           |false        |

### escapeCharacters

If "true" escape characters that might be invalid (e.g. tabs and new lines).
Per default this will replace all characters that are invalid according the rules for 
PostgreSQL import files (http://www.postgresql.org/docs/9.3/static/sql-copy.html#AEN69268).
If further customization is needed (which should not be the case normally) you can use `escapeCharactersList`.

### escapeCharactersList

If set a custom character replacement is used instead of the default one.
A definition has the form
```
(FIND_CHAR=REPLACE_CHAR)
```
where `FIND_CHAR` is exact one character and `REPLACE_CHAR` is a replacement string.
Multiple definition can be given by separating them with comma.
When defining your own definitions you have to make sure that the result is compatible to the
PostgreSQL CSV import file format (http://www.postgresql.org/docs/9.3/static/sql-copy.html#AEN69268).
Also make sure that each character is only replaced by one character.
You can insert some special characters as key by escaping them:

|Escaped key | Name    |
|------------|---------|
| `\t`       | Tabstop |
| `\n`       | Line feed (Newline) |
| `\r`       | Carriage return |
| `\'`       | Single quote |
| `\"`       | Double quote |

An example definition could look like this:
```
escapeCharactersList=(\n=_),(\r=_),(\t=\t)
```

### clobber.visualisation

If "true" overwrite any existing resolver_vis_map.annis file in the output folder.

### clobber.corpus_annotation

If "true" overwrite any existing corpus_annotation.annis file in the output folder.

### corpusName

If set use this name as the toplevel corpus name instead of the one provided by Pepper.

### mergeTextsWithTimeline
If "true" and the document has a timeline merge texts of one document to one text and create an artificial tokenization based on the timeline.

### excludeSingleDomType
If there is only a single edge type for dominance relations and this property is set, 
don't output the named component entries (but the general one with the empty name).

### mergeVisualisation
If there is an existing resolver visualization mapping table attempt to merge the existing entries instead of overwriting them.

