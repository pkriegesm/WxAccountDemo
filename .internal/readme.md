# WxAccountDemo - Release creation

The script and Ansible playbook in this folder have the intention to simplify the task of creating a release for this project in its GitHub repository, and the packaged assets needed for dependant projects or deployments.

The script will use the following command line tools:

    - tar
    - zip
    - git
    - gh

Additionally, if the tag needs to be signed, a gpg key needs to be there and configured GitHub in order for it to work.
