# practicallitest

A Clojure library designed to ... well, that part is up to you.

## Usage

2022-12-18 12.02
two endpoints: 

1: 
GET
http://localhost:8000/ 
result
<h1>Hello Clojure Server world!</h1>

2:
POST
http://localhost:8000/test
body:
{
"text": "hello chr"
}
result:
[#object[cheshire.core$generate_string 0x6f7db1df "cheshire.core$generate_string@6f7db1df"
] {
:json true,
:response "hello chr"
}
]

## License

Copyright © 2022 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
