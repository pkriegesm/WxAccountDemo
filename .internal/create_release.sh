#!/bin/bash

if command -v git &> /dev/null; then
#    echo "Git is installed."
    echo -n "."
else
  echo "git is not installed."
  exit 1
fi

if command -v zip &> /dev/null; then
#   echo "gh is installed."
    echo -n "."
else
  echo "zip is not installed."
  exit 1
fi

if command -v gh &> /dev/null; then
#   echo "gh is installed."
    echo -n "."
else
  echo "gh is not installed."
  exit 1
fi
# Prompt for the github user and token
echo
# read -p "Enter your GitHub User: " ghusr
# read -s -p "Enter your GitHub Password: " pwdgh
echo
read -p "Enter the tag to be created: " ghtag
initial_folder=$(pwd)
cd ..

if gh auth status > /dev/null 2>&1; then
    echo "Check that you are logged in to the correct GitHub account"
    echo "$(gh auth status)"
    echo "."
else
    echo "gh is not logged in. Follow the instructions to log in..."
    gh auth login
fi

# get the project name
project_name=$(gh repo view --json name -q '.name')
echo "Using '${project_name}' as package name for archive files"

echo -n "."
tar -czf ./.internal/${project_name}.tar.gz \
    --exclude='./.git' \
    --exclude='./.gitignore' \
    --exclude='./.github' \
    --exclude='./.internal' \
    --exclude='*.tar' \
    --exclude='*.gz' \
    .

echo -n "."
zip -r ./.internal/${project_name}.zip \
    --exclude=".git" \
    --exclude=".gitignore" \
    --exclude=".github" \
    --exclude=".internal" \
    --exclude="*.tar" \
    --exclude="*.gz" \
    . > /dev/null 2>&1
echo -n "."

# Prompt for a yes/no input
read -n 1 -r -p "A release will be created for the '${project_name}' repository with the version '${ghtag}'. Do you want to continue? [Y/n]" releaseConfirmation
echo
echo -n "."
# Check if the input is 'y', 'Y', or empty
case $releaseConfirmation in
  ""|y|Y)
    read -n 1 -r -p "Do you want to sign the '${ghtag}' tag? [Y/n]" signTag
    echo
    case $signTag in
        ""|y|Y)
            # this is needed to be able to input the passphrase from the terminal
            export GPG_TTY=$(tty)
            git tag -s $ghtag -m "Release '${ghtag}'"
            ;;
        *)
            git tag -m "Release '${ghtag}'"
            ;;
    esac
    git push origin $ghtag

    read -n 1 -r -p "Do you want to draft this release '${ghtag}'? [Y/n]" draftRelease
    echo
    case $draftRelease in
        ""|y|Y)
            gh release create $ghtag --draft --title "Release '${ghtag}'"
            ;;
        *)
            gh release create $ghtag --title "Release '${ghtag}'"
            ;;
    esac
    gh release upload $ghtag ${initial_folder}/${project_name}.tar.gz
    gh release upload $ghtag ${initial_folder}/${project_name}.zip
    gh release view $ghtag
    ;;
  *)
    echo "Release creation aborted..."
    exit 1
    ;;
esac

